/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.expense_tracker.dto.ExpenseGroupDTO;
import com.demo.expense_tracker.model.ExpenseGroup;
import com.demo.expense_tracker.services.ExpenseGroupService;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/expense-groups")
public class ExpenseGroupController extends GenericController<ExpenseGroup, ExpenseGroupDTO, Long>{
    @Autowired
    public ExpenseGroupController(ExpenseGroupService expenseGroupService){
        super(expenseGroupService);
    }
}
