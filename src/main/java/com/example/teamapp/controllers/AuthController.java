package com.example.teamapp.controllers;

import com.example.teamapp.dto.UserDto;
import com.example.teamapp.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class AuthController {

    private final UserService userService;

    final PasswordEncoder passwordEncoder;


    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @GetMapping("/login")
    public String getLoginView(){
        return "login";
    }

    @PostMapping("/registration")
    public String userRegistration(UserDto user, Map<String, Object> model){
        final boolean isUserExist = userService.isUserExist(user);
        if(!isUserExist){
            userService.saveUser(user);
            model.put("message", "User registration successful");
            return "login";
        }
        else{
            model.put("message", "User exist");
            return "registration";
        }
    }

    @GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}