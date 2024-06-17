package com.demo.expense_tracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootTest
@ActiveProfiles("test")
class ExpenseTrackerApplicationTests {

	// @BeforeAll
    // public static void setup() {
    //     Dotenv dotenv = Dotenv.configure().directory("C:/Users/mihajlo.vuckovic/Desktop/Expense Tracker").load();
    //     dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    // }

	@Test
	void contextLoads() {
	}

}
