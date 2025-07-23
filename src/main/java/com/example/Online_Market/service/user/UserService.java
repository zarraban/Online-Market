package com.example.Online_Market.service.user;

import com.example.Online_Market.dto.UserDto;
import com.example.Online_Market.entity.user.User;
import com.example.Online_Market.exception.FileException;
import com.example.Online_Market.repository.role.RoleRepository;
import com.example.Online_Market.repository.user.UserRepository;
import com.example.Online_Market.service.BaseService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service("userService")
@Slf4j
public class UserService implements BaseService<UserDto, User> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(
        @Qualifier("userRepository") UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        @Qualifier("roleRepository") RoleRepository roleRepository
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }



    @Override
    @Transactional
    public User save(UserDto entity) throws NotFoundException {
        if(entity == null){
            log.error("Given entity to save() was null");
            throw new NullPointerException("User dto can't be null");
        }

        if(!roleRepository.findRoleByName(entity.getRole())){

            log.error("Role that was given by User doesn't exist. UserEmail={}", entity.getEmail());
            throw new NotFoundException("Role was no found");
        }
        User user = new User();
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setPhone(entity.getPhone());
        user.setEmail(entity.getEmail());
        user.setCountry(entity.getCountry());
        user.setProfilePhoto("/userPhotos/standardUserPhoto.webp");
        user.setPassword(passwordEncoder.encode(entity.getPassword()));
        return userRepository.save(user);


    }

    @Override
    @Transactional
    public boolean deleteByEmail(String email) {
        if(email == null){
            log.error("Given email to deleteByEmail() was null");
            throw new NullPointerException("Email can't be null");
        }
        return userRepository.deleteByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<User>> getAll() {
        List<User> users = userRepository.findAll();
        return Optional.of(users);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getByEmail(String email) {
        if(email == null){
            log.error("Given email to getByEmail() was null");
            throw new NullPointerException("Email can't be null");
        }
        User user = userRepository.findByEmail(email);
        return Optional.of(user);
    }


    @Transactional
    public boolean updateUserPhoto(String email, String photoPath){
        if(email == null){
            log.error("Given email to updateUserPhoto() was null");
            throw new NullPointerException("Email can't be null");
        }
        if(photoPath == null){
            log.error("Given photoPath to updateUserPhoto() was null");
            throw new NullPointerException("PhotoPath can't be null");
        }

        File file = new File(photoPath);
        if(!file.exists() || !file.isFile()){

            log.error("File with filepath = [{}], doesn't exist or has invalid format", file.getPath());
            throw new FileException("FIle doesn't exist or not valid!");
        }

        User user = userRepository.findByEmail(email);
        if(user==null){
            log.error("User with email = [{}] was not found!", email);
            throw new FileException("User with email= "+ email +" was not found!");
        }

        try{
            user.setProfilePhoto(photoPath);
            userRepository.save(user);
            return true;
        }catch (Exception e){
            log.error("Error occurred while saving user(Email = [{}]) with photo=[{}]",email,photoPath);
            return false;
        }

    }
}
