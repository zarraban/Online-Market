package com.example.Online_Market.service.user;

import com.example.Online_Market.dto.UserDto;
import com.example.Online_Market.entity.user.User;
import com.example.Online_Market.exception.FileException;
import com.example.Online_Market.repository.role.RoleRepository;
import com.example.Online_Market.repository.user.UserRepository;
import com.example.Online_Market.service.BaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("userService")
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
    public User save(UserDto entity) {
        Objects.requireNonNull(entity, "Parameter [entity] must not be null!");
        
        if(roleRepository.findRoleByName(entity.getRole())){
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
        return null;
    }

    @Override
    @Transactional
    public boolean deleteByEmail(String email) {
        Objects.requireNonNull(email, "Parameter [email] must not be null!");
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
        Objects.requireNonNull(email, "Parameter [email] must not be null!");
        User user = userRepository.findByEmail(email);
        return Optional.of(user);
    }


    @Transactional
    public boolean updateUserPhoto(String email, String photoPath){
        Objects.requireNonNull(email, "Parameter [email] must not be null!");
        Objects.requireNonNull(photoPath, "Parameter [photoPath] must not be null!");

        File file = new File(photoPath);
        if(!file.exists() || !file.isFile()){

            //TODO add logs
            throw new FileException("FIle doesn't exist or not valid!");
        }

        User user = userRepository.findByEmail(email);
        if(user==null){
            //TODO add logs
            throw new FileException("User with email= "+ email +" was not found!");
        }

        try{
            user.setProfilePhoto(photoPath);
            userRepository.save(user);
            return true;
        }catch (Exception e){
            //TODO add logger with this message
            return false;
        }

    }
}
