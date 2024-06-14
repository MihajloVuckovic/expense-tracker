/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.demo.expense_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import io.github.cdimascio.dotenv.Dotenv;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.demo.expense_tracker.utils.TokenUtils;

import io.netty.handler.codec.http.HttpHeaders;
 
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
    
    // @BeforeAll
    // public static void setup() {
    //     Dotenv dotenv = Dotenv.configure().directory("C:/Users/mihajlo.vuckovic/Desktop/Expense Tracker").load();
    //     dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    // }

    @Test
    void GetIncomeGroup() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/dashboard/income-groups"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}