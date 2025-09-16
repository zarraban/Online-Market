package com.example.Online_Market.controller;


import com.example.Online_Market.dto.UserDto;
import com.example.Online_Market.entity.user.User;
import com.example.Online_Market.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AuthController {
    private final UserService userService;
    public AuthController(
            @Qualifier("userService")UserService userService
            ){
        this.userService = userService;
    }
    @GetMapping("/registration")
    public String registrationPage(Model model, Authentication authentication){
        model.addAttribute("user", new UserDto());
        boolean isAuth = authentication!=null && authentication.isAuthenticated();
        if(isAuth){
            model.addAttribute("layout", "/layouts/layoutLogged.html");
        }else {
            model.addAttribute("layout", "/layouts/layout.html");
        }
        return "registration";
    }

    @GetMapping("/login")
    public String loginPage(Model model, Authentication authentication){
        boolean isAuth = authentication!=null && authentication.isAuthenticated();
        if(isAuth){
            model.addAttribute("layout", "/layouts/layoutLogged.html");
        }else {
            model.addAttribute("layout", "/layouts/layout.html");
        }
        return "login";
    }

    @PostMapping("/registration/save/user")
    public String saveUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("errorValidation", "Fill fields correctly");
            return "registration";
        }

        try {
            userService.save(userDto);
        }
        catch (CompromisedPasswordException e) {
            model.addAttribute("errorCompromisedPassword","Entered password is compromised! Try another!");
            return "registration";
        }
        catch (Exception e){
            model.addAttribute("errorSave","Error while saving user");
            return "registration";
        }


        Optional<User> userOptional = userService.getByEmail(userDto.getEmail());


        if(userOptional.isPresent()){
            return "login";
        }else {
            model.addAttribute("errorUser","User was not saved. Try again!");
            return "registration";
        }

    }


}
