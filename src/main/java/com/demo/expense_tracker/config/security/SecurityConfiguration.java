/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${JWT_SECRET}")
	private String secret;

    @Autowired
	UserDetailsService userDetailsService;

	@Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;
	
	@Bean
	AuthenticationManager authenticationManagerBean(AuthenticationConfiguration conf) throws Exception {
		return conf.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder getPasswordEncoder() {
		// Map<String, PasswordEncoder> encoders = new HashMap<>();
		// encoders.put("bcrypt", new BCryptPasswordEncoder());
		// DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
		// passwordEncoder.setDefaultPasswordEncoderForMatches(encoders.get("bcrypt"));
		return new BCryptPasswordEncoder();
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
				.rememberMe(rememberMe -> rememberMe.key(secret).tokenValiditySeconds(86400).rememberMeParameter("remember-me"))
				.logout((logout) -> logout.logoutUrl("/api/logout").logoutSuccessUrl("/api/login").logoutSuccessHandler(logoutSuccessHandler))
                .build();
	}
}
