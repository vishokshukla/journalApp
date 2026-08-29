package com.learnboot.journalapp.service;

import com.learnboot.journalapp.entity.User;
import com.learnboot.journalapp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsername() {
        Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(User.builder().username("Vishok").password("124563").roles(new ArrayList<>()).build());
        UserDetails vishok = userDetailsServiceImpl.loadUserByUsername("Vishok");
        Assertions.assertNotNull(vishok);
    }
}