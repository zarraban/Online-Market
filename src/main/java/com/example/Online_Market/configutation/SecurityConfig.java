package com.example.Online_Market.configutation;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Profile("prod")
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
        http
                .redirectToHttps(withDefaults())
                .authorizeHttpRequests((request)->
                request.requestMatchers(
                        "/registration",
                        "/login",
                        "/error",
                        "/main",
                        "contact",
                        "/registration/save/user",
                        "/carouselPhotos/**",
                        "/footerPhotos/**",
                        "/logo/**",
                        "/siteIcon/**",
                        "/userPhotos/**",
                        "/css/**",
                        "/js/**").permitAll());
        http.formLogin((login)->
                login.loginPage("/login")
                        .successForwardUrl("/main"));
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public CompromisedPasswordChecker passwordChecker(){
        //Realization of CompromisedPasswordChecker to check whether you password is not strong or good enough
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
