/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.expense_tracker.model.User;
import com.demo.expense_tracker.utils.TokenUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Value("${JWT_EXPIRATION}")
    private Long expiration;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    
    @PostMapping
    public ResponseEntity<String> login(@Schema(implementation = LoginRequest.class, description = "User login details")
        @RequestBody User user, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword());
        Authentication auth = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String jwt = tokenUtils.generateToken(userDetails);
        ResponseCookie cookie = ResponseCookie.from("accessToken", jwt)
            .httpOnly(true)
            .secure(true)
                .sameSite("Strict")
            .path("/")
            .maxAge(expiration)
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return new ResponseEntity<>("Successful login", HttpStatus.OK);
    }
    
    @Getter
    @Setter
    public static class LoginRequest {
        @Schema(description = "Username of the user", example = "user1", required = true)
        private String username;

        @Schema(description = "Password of the user", example = "pass1", required = true, format = "password")
        private String password;

    }
}
