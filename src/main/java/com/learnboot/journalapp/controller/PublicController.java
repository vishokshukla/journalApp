package com.learnboot.journalapp.controller;

import com.learnboot.journalapp.entity.User;
import com.learnboot.journalapp.service.UserDetailsServiceImpl;
import com.learnboot.journalapp.service.UserService;
import com.learnboot.journalapp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/signup")
    public void signUp(@RequestBody User user) {
         userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (AuthenticationException e) {
            log.error("exception occurred while login", e);
            return new ResponseEntity<>("Incorrect Username and Password", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

}
