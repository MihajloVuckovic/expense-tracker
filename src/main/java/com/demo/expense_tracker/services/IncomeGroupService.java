/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.IncomeGroupDTO;
import com.demo.expense_tracker.model.Income;
import com.demo.expense_tracker.model.IncomeGroup;
import com.demo.expense_tracker.repositories.IncomeGroupRepository;
import com.demo.expense_tracker.repositories.IncomeRepository;
import com.demo.expense_tracker.utils.TokenUtils;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class IncomeGroupService extends GenericServiceImpl<IncomeGroup, IncomeGroupDTO, Long> {
    
    private final TokenUtils tokenUtils;

    @Autowired
    private IncomeGroupRepository incomeGroupRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    private final ModelMapper mapper;

    public IncomeGroupService(IncomeGroupRepository incomeGroupRepository){
        super(incomeGroupRepository);
        this.tokenUtils = new TokenUtils();
        this.mapper = new ModelMapper();
    }
    @Override
    protected Class<IncomeGroupDTO> getTypeOfDTO() {
        return IncomeGroupDTO.class;
    }

    @Override 
    protected String entityName(){
        return IncomeGroup.class.getSimpleName();
    }

    @Override
    public Page<IncomeGroupDTO> findAll(Pageable pageable, Map<String, String> map) {
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<IncomeGroup> incomeGroups = incomeGroupRepository.findAll(pageable);
        
        for(IncomeGroup incomeGroup : incomeGroups){
            List<Income> incomes = incomeRepository.findTop5ByIncomeGroupAndUser_idOrderByIncomeDateDesc(incomeGroup, user_id);
            incomeGroup.setIncomes(incomes);
        }
        return incomeGroups.map(incomeGroup -> mapper.map(incomeGroup, getTypeOfDTO()));
    }

}
