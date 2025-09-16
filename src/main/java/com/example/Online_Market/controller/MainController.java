package com.example.Online_Market.controller;


import com.example.Online_Market.service.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final UserService userService;
    public MainController(
            @Qualifier("userService") UserService userService
            ) {
        this.userService=userService;
    }


    @GetMapping("/main")
    public String getMainPage(Model model, Authentication authentication){
        boolean isAuth = authentication!=null && authentication.isAuthenticated();
        if(isAuth){
            model.addAttribute("layout", "/layouts/layoutLogged.html");
        }else {
            model.addAttribute("layout", "/layouts/layout.html");
        }
        int numberOfUsers = userService.getUsersNumber();
        model.addAttribute("usersCount", formatUsersNumber(numberOfUsers));
        return "main";
    }

    private String formatUsersNumber(int numberOfUsers){
        if(numberOfUsers==0){
            return "No one is";
        } else if (numberOfUsers == 1) {
            return numberOfUsers + " is";
        }else if(numberOfUsers>1) {
            return numberOfUsers + " are";
        }
        return "";
    }
}
