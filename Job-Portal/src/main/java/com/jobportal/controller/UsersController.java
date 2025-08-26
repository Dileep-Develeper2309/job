package com.jobportal.controller;

import com.jobportal.entity.Users;
import com.jobportal.entity.UsersType;
import com.jobportal.service.UsersService;
import com.jobportal.service.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.usertype.UserType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    @GetMapping("/register")
    public String register(Model model){
        List<UsersType> userTypes = usersTypeService.getAll();
        model.addAttribute("getAllTypes",userTypes);
        model.addAttribute("user",new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userregistration(@Valid Users users,Model model){
        //log.info("Users::"+users);
        Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());

        if (optionalUsers.isPresent()){
            model.addAttribute("error","Email already registered.Please try with different one");
            List<UsersType> userTypes = usersTypeService.getAll();
            model.addAttribute("getAllTypes",userTypes);
            model.addAttribute("user",new Users());
            return "register";
        }

        usersService.addNewUser(users);
        return "redirect:/dashboard/";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if(authentication!=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }
}
