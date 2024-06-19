package com.demo.expense_tracker;

import com.demo.expense_tracker.model.Role;
import com.demo.expense_tracker.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExpenseTrackerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	public static void setup() {
		Dotenv dotenv = Dotenv.configure()
				.directory("C:/Users/mihajlo.vuckovic/Desktop/Expense Tracker")
				.load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
	}

	@Test
	void contextLoads() {
	}

	@Test
	void postUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/user").contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\": \"standard\", \"password\": \"standard\" , \"email\":\"standard\", \"role\":\"ROLE_STANDARD\"}")
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void getIncomeGroups() throws  Exception{
		User user = new User();
		user.setUsername("standard");
		user.setPassword("password");
		user.setEmail("standard@example.com");
		user.setRole(Role.ROLE_STANDARD);

		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/dashboard/income-groups")
		).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	void getReminder() throws  Exception{
		User user = new User();
		user.setUsername("standard");
		user.setPassword("password");
		user.setEmail("standard@example.com");
		user.setRole(Role.ROLE_STANDARD);

		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/dashboard/reminders")
		).andExpect(MockMvcResultMatchers.status().isForbidden());

	}
}
