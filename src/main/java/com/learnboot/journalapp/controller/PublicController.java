package com.learnboot.journalapp.controller;

import com.learnboot.journalapp.entity.User;
import com.learnboot.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public boolean createUser(@RequestBody User user) {
        return userService.saveNewUser(user);
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

}
