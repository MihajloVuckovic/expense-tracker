/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.UserDTO;
import com.demo.expense_tracker.model.User;
import com.demo.expense_tracker.repositories.UserRepo;
import com.demo.expense_tracker.utils.TokenUtils;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class UserService extends GenericServiceImpl<User, UserDTO, Long> {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    private final ModelMapper mapper;

    private final TokenUtils tokenUtils;
    
    @Autowired
    public UserService(UserRepo userRepo){
        super(userRepo);
        this.mapper= new ModelMapper();
        this.tokenUtils = new TokenUtils();
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

    @Override
    public void update(UserDTO dto, Long id) {
        final User updatedUser = userRepo.findById(id)
                .orElseThrow(null);
        Long user_id = tokenUtils.getUserIdFromToken();
        if(user_id != id){
            throw new RuntimeException("You cannot change another user!");
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        mapper.map(dto, updatedUser);
        userRepo.save(updatedUser);
    }
}
