package com.jobportal.controller;

import com.jobportal.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class JobPostActivityController {

    private final UsersService usersService;

    @GetMapping("/dashboard/")
    public String searchJobs(Model model){
        Object currentProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();
            model.addAttribute("username",currentUserName);
        }
        model.addAttribute("user",currentProfile);

        return "dashboard";
    }
}
