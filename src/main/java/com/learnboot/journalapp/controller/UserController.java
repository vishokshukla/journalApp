package com.learnboot.journalapp.controller;

import com.learnboot.journalapp.api.response.MiniWeatherResponse;
import com.learnboot.journalapp.entity.User;
import com.learnboot.journalapp.repository.UserRepository;
import com.learnboot.journalapp.service.UserService;
import com.learnboot.journalapp.service.WeatherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name="User Apis", description = "Read/Update/Delete User")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

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

    @GetMapping
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        MiniWeatherResponse weatherResponse = weatherService.getWeather("Lucknow");
        String greeting = "";
        if (weatherResponse != null) {
            greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + username + greeting, HttpStatus.OK);
    }

}
