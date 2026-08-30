package com.learnboot.journalapp.repository;

import com.learnboot.journalapp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    void testSaveNewUser() {
        List<User> usersForSA = userRepository.getUsersForSA();
        System.out.println(usersForSA);
    }
}