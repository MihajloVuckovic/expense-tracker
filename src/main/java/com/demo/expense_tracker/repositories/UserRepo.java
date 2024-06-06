/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.demo.expense_tracker.repositories;

import org.springframework.stereotype.Repository;

import com.demo.expense_tracker.dto.UserDTO;
import com.demo.expense_tracker.model.User;

/**
 *
 * @author mihajlo.vuckovic
 */
@Repository
public interface UserRepo extends GenericRepository<User, UserDTO, Long>{

}
