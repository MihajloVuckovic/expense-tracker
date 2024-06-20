/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.IncomeDTO;
import com.demo.expense_tracker.model.Income;
import com.demo.expense_tracker.model.QIncome;
import com.demo.expense_tracker.repositories.IncomeRepository;
import com.demo.expense_tracker.utils.TokenUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

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
        if(user_id != null){
            t.setUser_id(user_id);
        }
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
    public Page<IncomeDTO> findAll(Pageable pageable, Map<String, String> map) {
        QIncome qIncome = QIncome.income;
        BooleanExpression predicate = qIncome.isNotNull();
        Long user_id = tokenUtils.getUserIdFromToken();
        predicate = predicate.and(qIncome.user_id.eq(user_id));                 
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                switch (key) {
                    case "amount" -> predicate = predicate.and(qIncome.amount.eq(Double.valueOf(value)));
                    case "description" -> predicate = predicate.and(qIncome.description.containsIgnoreCase(value));
                    case "date" -> predicate = predicate.and(qIncome.incomeDate.eq(LocalDate.parse(value)));
                    case "income_group" -> predicate = predicate.and(qIncome.income_group_id.eq(Long.valueOf(value)));
                }
            }
        Page<Income> incomes = incomeRepository.findAll(predicate, pageable);
        return incomes.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Iterable<IncomeDTO> findAll(){
        Long user_id = tokenUtils.getUserIdFromToken();
        List<Income> incomes = incomeRepository.findByUser_id(user_id);
        return incomes.stream().map(income -> mapper.map(income, getTypeOfDTO()))
                        .collect(Collectors.toList());
    }

}
