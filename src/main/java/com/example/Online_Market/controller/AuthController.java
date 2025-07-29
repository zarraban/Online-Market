package com.example.Online_Market.controller;


import com.example.Online_Market.dto.UserDto;
import com.example.Online_Market.entity.user.User;
import com.example.Online_Market.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public String registrationPage(Model model){
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/registration/save/user")
    public String saveUser(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "redirect:/registration?error";
        }

        try {
            userService.save(userDto);
        }catch (Exception e){
            return "redirect:/registration?errorSave";
        }


        Optional<User> userOptional = userService.getByEmail(userDto.getEmail());


        if(userOptional.isPresent()){
            return "login";
        }else {
            return "redirect:/registration?errorUser";
        }

    }


}
