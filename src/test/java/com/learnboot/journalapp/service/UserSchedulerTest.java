package com.learnboot.journalapp.service;

import com.learnboot.journalapp.scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulerTest {

    @Autowired
    private UserScheduler userScheduler;

    @Test
    public void testFetchUserAndSendEmail() {
        userScheduler.fetchUsersAndSendEmail();
    }
}
