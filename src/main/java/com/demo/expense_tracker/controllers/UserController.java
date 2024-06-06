/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    public UserController(UserService userService){
        super(userService);
    }
}
