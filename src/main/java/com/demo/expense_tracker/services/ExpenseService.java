/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.ExpenseDTO;
import com.demo.expense_tracker.model.Expense;
import com.demo.expense_tracker.repositories.ExpenseRepository;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class ExpenseService extends GenericServiceImpl<Expense, ExpenseDTO, Long>{
    
    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository){
        super(expenseRepository);
    }
    @Override
    protected Class<ExpenseDTO> getTypeOfDTO() {
        return ExpenseDTO.class;
    }

}
