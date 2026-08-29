package com.learnboot.journalapp.service;

import com.learnboot.journalapp.entity.User;
import com.learnboot.journalapp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test
    void findUserByUsername() {
//        assertEquals(4, 3+1);
        User user = userRepository.findByUsername("Vishok");
//        assertNotNull(userRepository.findByUsername("Vishok"));
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,3,4",
            "3,3,6"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a+b);
    }
}