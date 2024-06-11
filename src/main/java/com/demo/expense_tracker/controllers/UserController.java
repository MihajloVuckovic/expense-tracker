/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.expense_tracker.dto.UserDTO;
import com.demo.expense_tracker.model.User;
import com.demo.expense_tracker.services.UserService;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends GenericController<User, UserDTO, Long> {
    
    public UserController(UserService userService){
        super(userService);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD", "ROLE_ADMIN"})
    public ResponseEntity<String> delete(Long id) {
        return super.delete(id);
    }

    @Override
    public ResponseEntity<User> create(User t) {
        return super.create(t);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD", "ROLE_ADMIN"})
    public ResponseEntity<UserDTO> update(UserDTO dto, Long id) {
        return super.update(dto, id);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDTO> findById(Long id) {
        return super.findById(id);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public Page<UserDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> allParams) {
        return super.findAll(page, size, sortBy, sortDir, allParams);
    }
}
