/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.expense_tracker.dto.ExpenseDTO;
import com.demo.expense_tracker.model.Expense;
import com.demo.expense_tracker.services.ExpenseService;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController extends GenericController<Expense, ExpenseDTO, Long> {
    @Autowired
    public ExpenseController(ExpenseService expenseService){
        super(expenseService);
    }
}
