/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.IncomeGroupDTO;
import com.demo.expense_tracker.model.IncomeGroup;
import com.demo.expense_tracker.repositories.IncomeGroupRepository;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class IncomeGroupService extends GenericServiceImpl<IncomeGroup, IncomeGroupDTO, Long> {
    @Autowired
    public IncomeGroupService(IncomeGroupRepository incomeGroupRepository){
        super(incomeGroupRepository);
    }
    @Override
    protected Class<IncomeGroupDTO> getTypeOfDTO() {
        return IncomeGroupDTO.class;
    }

}
