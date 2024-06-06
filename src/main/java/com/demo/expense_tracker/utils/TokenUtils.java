/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.demo.expense_tracker.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


/**
 *
 * @author mihajlo.vuckovic
 */
@Component
public class TokenUtils {
    
    @Value("${token.secret}")
	private String secret;

	@Value("${token.expiration}")
	private Long expiration;

	@Bean
	public Key getKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public boolean isTokenExpired(String token) {
		return getClaims(token).getExpiration().before(new Date());
	}

	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", userDetails.getUsername());
		claims.put("email", ((User) userDetails).getEmail());
		claims.put("authorities", userDetails.getAuthorities());
		claims.put("created", new Date(System.currentTimeMillis()));

		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
				.signWith(getKey())
				.compact();
	}
}
