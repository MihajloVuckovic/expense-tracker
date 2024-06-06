/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author mihajlo.vuckovic
 */
@NoArgsConstructor
@Getter
@Setter
public class ExpenseDTO implements Serializable{
    Long id;
    String description;
    Double amount;
    LocalDate expenseDate;
    Long expense_group_id;
    Long user_id;
}
