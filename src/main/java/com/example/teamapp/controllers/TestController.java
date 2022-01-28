package com.example.teamapp.controllers;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class TestController {


    public TestController() {

    }

    @GetMapping("/test")
    public String getTest(){
        return "test";
    }


}
