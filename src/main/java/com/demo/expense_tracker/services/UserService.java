/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.UserDTO;
import com.demo.expense_tracker.model.User;
import com.demo.expense_tracker.repositories.UserRepo;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class UserService extends GenericServiceImpl<User, UserDTO, Long> {
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepo userRepo){
        super(userRepo);
    }

    @Override
    protected Class<UserDTO> getTypeOfDTO() {
        return UserDTO.class;
    }

    @Override
    public User save(User t) {
        t.setPassword(passwordEncoder.encode(t.getPassword()));
        return super.save(t);
    }

    

}
