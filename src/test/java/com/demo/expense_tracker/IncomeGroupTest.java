/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.demo.expense_tracker;

import com.demo.expense_tracker.model.Role;
import com.demo.expense_tracker.model.User;
import com.demo.expense_tracker.utils.TokenUtils;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

/**
 *
 * @author mihajlo.vuckovic
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IncomeGroupTest {

    @Autowired
    private MockMvc mockMvc;

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