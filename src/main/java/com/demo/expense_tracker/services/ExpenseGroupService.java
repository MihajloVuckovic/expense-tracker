/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.ExpenseGroupDTO;
import com.demo.expense_tracker.model.Expense;
import com.demo.expense_tracker.model.ExpenseGroup;
import com.demo.expense_tracker.repositories.ExpenseGroupRepository;
import com.demo.expense_tracker.repositories.ExpenseRepository;
import com.demo.expense_tracker.utils.TokenUtils;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class ExpenseGroupService extends GenericServiceImpl<ExpenseGroup, ExpenseGroupDTO, Long> {
    
    @Autowired
    private ExpenseGroupRepository expenseGroupRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    private final ModelMapper mapper;

    private final TokenUtils tokenUtils;
    
    public ExpenseGroupService(ExpenseGroupRepository expenseGroupRepository){
        super(expenseGroupRepository);
        this.mapper = new ModelMapper();
        this.tokenUtils = new TokenUtils();
    }
    @Override
    protected Class<ExpenseGroupDTO> getTypeOfDTO() {
        return ExpenseGroupDTO.class;
    }

    @Override 
    protected String entityName(){
        return ExpenseGroup.class.getSimpleName();
    }

    @Override
    public Page<ExpenseGroupDTO> findAll(Pageable pageable) {
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<ExpenseGroup> expensesGroups = expenseGroupRepository.findAll(pageable);
        
        for(ExpenseGroup expenseGroup : expensesGroups){
            List<Expense> expenses = expenseRepository.findTop5ByExpenseGroupAndUser_idOrderByExpenseDateDesc(expenseGroup, user_id);
            expenseGroup.setExpenses(expenses);
        }
        return expensesGroups.map(expenseGroup -> mapper.map(expenseGroup, getTypeOfDTO()));
    }

    

    
}
