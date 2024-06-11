/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.ExpenseGroupDTO;
import com.demo.expense_tracker.model.ExpenseGroup;
import com.demo.expense_tracker.repositories.ExpenseGroupRepository;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class ExpenseGroupService extends GenericServiceImpl<ExpenseGroup, ExpenseGroupDTO, Long> {
    
    
    public ExpenseGroupService(ExpenseGroupRepository expenseGroupRepository){
        super(expenseGroupRepository);
    }
    @Override
    protected Class<ExpenseGroupDTO> getTypeOfDTO() {
        return ExpenseGroupDTO.class;
    }

    @Override 
    protected String entityName(){
        return ExpenseGroup.class.getSimpleName();
    }


}
