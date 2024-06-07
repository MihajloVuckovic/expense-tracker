/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.IncomeDTO;
import com.demo.expense_tracker.model.Income;
import com.demo.expense_tracker.repositories.IncomeRepository;
import com.demo.expense_tracker.utils.TokenUtils;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class IncomeService extends GenericServiceImpl<Income, IncomeDTO, Long> {
    
    private final TokenUtils tokenUtils;

    private final ModelMapper mapper;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository){
        super(incomeRepository);
        this.tokenUtils= new TokenUtils(); 
        this.mapper = new ModelMapper();
    }
    @Override
    protected Class<IncomeDTO> getTypeOfDTO() {
        return IncomeDTO.class;
    }

    @Override
    public Income save(Income t) {
        Long user_id = tokenUtils.getUserIdFromToken();
        t.setUser_id(user_id);
        return super.save(t);
    }

    @Override
    public void remove(Long id) {
        Long user_id = tokenUtils.getUserIdFromToken();
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        Income income = optionalIncome.get();
        if(income.getUser_id().equals(user_id)){
            super.remove(id);
        }
    }

    @Override
    public void update(IncomeDTO dto, Long id) {
        Long user_id = tokenUtils.getUserIdFromToken();
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        Income income = optionalIncome.get();
        if(income.getUser_id().equals(user_id)){
            super.update(dto, id);
        }
    }

    @Override
    public Page<IncomeDTO> findAll(Pageable pageable) {
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Income> incomes = incomeRepository.findByUser_id(user_id, pageable);
        return incomes.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Iterable<IncomeDTO> findAll(){
        Long user_id = tokenUtils.getUserIdFromToken();
        List<Income> incomes = incomeRepository.findByUser_id(user_id);
        return incomes.stream().map(income -> mapper.map(income, getTypeOfDTO()))
                        .collect(Collectors.toList());
    }


}
