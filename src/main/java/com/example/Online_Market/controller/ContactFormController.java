package com.example.Online_Market.controller;


import com.example.Online_Market.dto.CommentDto;
import com.example.Online_Market.entity.user.User;
import com.example.Online_Market.exception.BadWordException;
import com.example.Online_Market.service.comment.CommentService;
import com.example.Online_Market.service.profanityfilter.ProfanityFilterService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ContactFormController {
    private final ProfanityFilterService filterService;
    private final CommentService commentService;

    public ContactFormController(
            @Qualifier("profanityFilterService")ProfanityFilterService filterService,
            @Qualifier("commentService") CommentService commentService
    ){
        this.filterService = filterService;
        this.commentService = commentService;
    }
    @GetMapping("/contact")
    public String getContactFrom(Model model, Authentication authentication){
        model.addAttribute("commentDto", new CommentDto());
        boolean isAuth = authentication!=null && authentication.isAuthenticated();
        if(isAuth){
            model.addAttribute("layout", "layouts/layoutLogged");
        }else {
            model.addAttribute("layout", "layouts/layout");
        }
        return "contact";
    }

    @PostMapping("/contact/send/comment")
    public String sendComment(@Valid @ModelAttribute("commentDto") CommentDto comment,
                              Model model,
                              BindingResult bindingResult,
                              @AuthenticationPrincipal User user,
                              Authentication authentication){
        boolean isAuth = authentication!=null && authentication.isAuthenticated();
        if(isAuth){
            model.addAttribute("layout", "/layouts/layoutLogged.html");
        }else {
            model.addAttribute("layout", "/layouts/layout.html");
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("errorValidation", "Fill fields correctly!");
        }
        try {
            filterService.hasBadWords(comment.getText());

        }catch (BadWordException e){
            model.addAttribute("errorBadWords",
                    "Your message contains bad words! Try again without them!");
            return "contact";
        }
        try{
            commentService.addCommentToUser(user,comment);
        }catch (Exception e){
            log.error("Exception occurred during saving comment: {}", e.getMessage());
            model.addAttribute("errorSuccessSave", "Saving was not successful");
            return "contact";
        }


        return "contact";
    }
}
