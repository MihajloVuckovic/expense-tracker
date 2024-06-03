/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.IncomeDTO;
import com.demo.expense_tracker.model.Income;
import com.demo.expense_tracker.repositories.IncomeRepository;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class IncomeService extends GenericServiceImpl<Income, IncomeDTO, Long> {
    @Autowired
    public IncomeService(IncomeRepository incomeRepository){
        super(incomeRepository); 
    }
    @Override
    protected Class<IncomeDTO> getTypeOfDTO() {
        return IncomeDTO.class;
    }


}
