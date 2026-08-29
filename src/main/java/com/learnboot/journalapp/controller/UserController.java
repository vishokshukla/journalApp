package com.learnboot.journalapp.controller;

import com.learnboot.journalapp.entity.JournalEntry;
import com.learnboot.journalapp.entity.User;
import com.learnboot.journalapp.repository.UserRepository;
import com.learnboot.journalapp.service.JournalEntryService;

import com.learnboot.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User userByUsername = userService.findUserByUsername(username);
            userByUsername.setUsername(user.getUsername());
            userByUsername.setPassword(user.getPassword());
            userService.saveNewUser(userByUsername);
            return new ResponseEntity<>(userByUsername, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userRepository.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
