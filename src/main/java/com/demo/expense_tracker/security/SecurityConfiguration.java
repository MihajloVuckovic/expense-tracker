/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author mihajlo.vuckovic
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class SecurityConfiguration {
    @Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	AuthenticationManager authenticationManagerBean(AuthenticationConfiguration conf) throws Exception {
		return conf.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder getPasswordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put("bcrypt", new BCryptPasswordEncoder());
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(encoders.get("bcrypt"));
		return passwordEncoder;
	}
	
	@Bean
	AuthTokenFilter authTokenFilterBean(AuthenticationConfiguration conf) throws Exception {
		AuthTokenFilter authTokenFilter = new AuthTokenFilter();
		authTokenFilter.setAuthenticationManager(conf.getAuthenticationManager());
		return authTokenFilter;
	}
	
	@Bean
	SecurityFilterChain configure(HttpSecurity http, AuthenticationConfiguration conf) throws Exception {
		return http.csrf(csrf -> csrf
                .disable())
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authTokenFilterBean(conf), UsernamePasswordAuthenticationFilter.class)
                .build();
	}
}
