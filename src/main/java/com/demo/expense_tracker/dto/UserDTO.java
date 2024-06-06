/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.dto;

import com.demo.expense_tracker.model.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author mihajlo.vuckovic
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    Long id;
    String email;
    String username;
    String password;
    Role role;
}
