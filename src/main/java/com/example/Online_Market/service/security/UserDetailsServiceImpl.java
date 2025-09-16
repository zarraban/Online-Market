package com.example.Online_Market.service.security;

import com.example.Online_Market.entity.user.User;
import com.example.Online_Market.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(
        @Qualifier("userRepository") UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }

    /**
     * @param username the username identifying the user whose data is required.
     * @return UserDetail implementation org.springframework.security.core.userdetails.User
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if(user == null){
            log.error("User with email= {} was not found during loadUserByUsername() in UserDetailsServiceImpl", username);
            throw new UsernameNotFoundException(String.format("User with email [%s] was not found", username));
        }
        
        List<GrantedAuthority> grantedAuthorities = user.getRoles()
                        .stream()
                        .map(role -> (GrantedAuthority)new SimpleGrantedAuthority(role.getName()))
                        .toList();

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                grantedAuthorities
        );

    }
}
