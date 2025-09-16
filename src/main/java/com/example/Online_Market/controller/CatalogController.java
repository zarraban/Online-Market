package com.example.Online_Market.controller;


import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogController {



    @GetMapping("/catalog")
    public String catalogPage(Model model, Authentication authentication){
        boolean isAuth = authentication!=null && authentication.isAuthenticated();
        if(isAuth){
            model.addAttribute("layout", "/layouts/layoutLogged.html");
        }else {
            model.addAttribute("layout", "/layouts/layout.html");
        }
        return "catalog";
    }
}
