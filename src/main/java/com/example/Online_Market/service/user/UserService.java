package com.example.Online_Market.service.user;

import com.example.Online_Market.dto.UserDto;
import com.example.Online_Market.entity.user.User;
import com.example.Online_Market.repository.role.RoleRepository;
import com.example.Online_Market.repository.user.UserRepository;
import com.example.Online_Market.service.BaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public boolean deleteByEmail(String email) {
        Objects.requireNonNull(email, "Parameter [email] must not be null!");
        return userRepository.deleteByEmail(email);
    }

    @Override
    public Optional<List<User>> getAll() {
        List<User> users = userRepository.findAll();
        return Optional.of(users);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        Objects.requireNonNull(email, "Parameter [email] must not be null!");
        User user = userRepository.findByEmail(email);
        return Optional.of(user);
    }
}
