package com.example.Online_Market.configutation;

import com.example.Online_Market.service.user.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class OwnAuthenticationProvider implements AuthenticationProvider {

    private PasswordEncoder passwordEncoder;
    private UserDetailsService userService;

    public OwnAuthenticationProvider(
            UserDetailsService userService,
            PasswordEncoder passwordEncoder
    ){
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }
    /**
     * @param authentication the authentication request object.
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userService.loadUserByUsername(username);
        if(passwordEncoder.matches(password, userDetails.getPassword())){
            // We can add some other validations here (example: age validation)
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    userDetails.getAuthorities()
            );
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }

    /**
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
