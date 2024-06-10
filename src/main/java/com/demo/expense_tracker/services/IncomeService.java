/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.time.LocalDate;
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
    protected String entityName(){
        return Income.class.getSimpleName();
    }

    @Override
    public Income save(Income t) {
        Long user_id = tokenUtils.getUserIdFromToken();
        t.setUser_id(user_id);
        t.setIncomeDate(LocalDate.now());
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

    public Page<IncomeDTO> filterAmount(Pageable pageable, Double amount){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Income> incomesAmount = incomeRepository.findByUser_idAndAmount(user_id, amount, pageable);
        return incomesAmount.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Page<IncomeDTO> filterDescription(Pageable pageable, String description){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Income> incomesDescription = incomeRepository.findByUser_idAndDescriptionContaining(user_id, description, pageable);
        return incomesDescription.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Page<IncomeDTO> filterDate(Pageable pageable, LocalDate date){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Income> incomesDate = incomeRepository.findByUser_idAndIncomeDate(user_id, date, pageable);
        return incomesDate.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Page<IncomeDTO> filterAmountAndDescription(Pageable pageable, Double amount, String description){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Income> incomesDate = incomeRepository.findByUser_idAndAmountAndDescriptionContaining(user_id, amount, description, pageable);
        return incomesDate.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Page<IncomeDTO> filterAmountAndDate(Pageable pageable, Double amount, LocalDate date){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Income> incomesDate = incomeRepository.findByUser_idAndAmountAndIncomeDate(user_id, amount, date, pageable);
        return incomesDate.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Page<IncomeDTO> filterDateAndDescription(Pageable pageable, LocalDate date, String description){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Income> incomesDate = incomeRepository.findByUser_idAndIncomeDateAndDescriptionContaining(user_id, date, description, pageable);
        return incomesDate.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Page<IncomeDTO> filterAmountAndDescriptionAndIncomeDate(Pageable pageable, Double amount, String description, LocalDate date){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Income> incomesDate = incomeRepository.findByUser_idAndIncomeDateAndDescriptionContainingAndAmount(user_id, date, description, amount, pageable);
        return incomesDate.map(income -> mapper.map(income, getTypeOfDTO()));
    }
}
