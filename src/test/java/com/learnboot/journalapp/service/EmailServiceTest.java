package com.learnboot.journalapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testEmailService() {
        emailService.sendMail("vishok.shukla@zohomail.in", "Java Email Test", "Hi Vishok!" +
                "This is java mail test.");
    }

}