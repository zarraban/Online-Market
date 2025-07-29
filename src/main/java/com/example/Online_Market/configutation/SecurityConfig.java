package com.example.Online_Market.configutation;


import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        //FOR TESTING
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
        http.authorizeHttpRequests((request)->
                request.requestMatchers(
                        "/registration",
                        "/login",
                        "/error",
                        "/carouselPhotos/**",
                        "/footerPhotos/**",
                        "/logo/**",
                        "/siteIcon/**",
                        "/userPhotos/**",
                        "/css/**",
                        "/js/**").permitAll());
        http.formLogin((login)->
                login.loginPage("/login"));
        http.httpBasic(withDefaults());
        return http.build();
    }
}
