package com.learnboot.journalapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest
@EnableTransactionManagement
@EnableScheduling
class JournalAppApplicationTests {

	@Test
	void contextLoads() {
	}

}
