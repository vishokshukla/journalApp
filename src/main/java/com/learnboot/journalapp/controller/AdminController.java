package com.learnboot.journalapp.controller;

import com.learnboot.journalapp.cache.AppCache;
import com.learnboot.journalapp.entity.User;
import com.learnboot.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> allUsers = userService.findAllUsers();
        if (!allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createAdminUser(@RequestBody User user) {
        userService.saveAdmin(user);
    }

    @GetMapping("/clear-app-cache")
    public void clearAppCache(@RequestBody User user) {
        appCache.init();
    }
}
