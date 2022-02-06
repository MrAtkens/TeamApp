package com.example.teamapp.controllers;


import com.example.teamapp.models.Auth.UserEntity;
import com.example.teamapp.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class AccountPageController {

    private final UserService userService;

    public AccountPageController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/add-post")
    public String addPost(){
        return "add-post";
    }

    @PreAuthorize("hasRole('USERS')")
    @GetMapping("/users")
    public String users(){
        return "users";
    }

    @PreAuthorize("hasRole('USERS')")
    @PostMapping("/findUsers")
    public String findUsers(@RequestParam String username, Map<String, Object> model){
        List<UserEntity> users = userService.findUsers(username);
        model.put("users", users);
        return "index";
    }
}