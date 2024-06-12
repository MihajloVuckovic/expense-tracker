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

import com.demo.expense_tracker.dto.ExpenseDTO;
import com.demo.expense_tracker.model.Expense;
import com.demo.expense_tracker.model.QExpense;
import com.demo.expense_tracker.repositories.ExpenseRepository;
import com.demo.expense_tracker.utils.TokenUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class ExpenseService extends GenericServiceImpl<Expense, ExpenseDTO, Long>{

    private final TokenUtils tokenUtils;

    private final ModelMapper mapper;
    
    @Autowired
    private ExpenseRepository expenseRepository;

    
    public ExpenseService(ExpenseRepository expenseRepository){
        super(expenseRepository);
        this.tokenUtils= new TokenUtils();
        this.mapper = new ModelMapper();
    }
    @Override
    protected Class<ExpenseDTO> getTypeOfDTO() {
        return ExpenseDTO.class;
    }

    @Override 
    protected String entityName(){
        return Expense.class.getSimpleName();
    }

    @Override
    public Expense save(Expense t) {
        Long user_id = tokenUtils.getUserIdFromToken();
        t.setUser_id(user_id);
        t.setExpenseDate(LocalDate.now());
        return super.save(t);
    }

    @Override
    public void remove(Long id) {
        Long user_id = tokenUtils.getUserIdFromToken();
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        Expense expense = optionalExpense.get();
        if(expense.getUser_id().equals(user_id)){
            super.remove(id);
        }
    }

    @Override
    public void update(ExpenseDTO dto, Long id) {
        Long user_id = tokenUtils.getUserIdFromToken();
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        Expense expense = optionalExpense.get();
        if(expense.getUser_id().equals(user_id)){
            super.update(dto, id);
        }
        
    }

    @Override
    public Page<ExpenseDTO> findAll(Pageable pageable, Map<String, String> map) {
        QExpense qExpense = QExpense.expense;
        BooleanExpression predicate = qExpense.isNotNull();
        Long user_id = tokenUtils.getUserIdFromToken();
        predicate = predicate.and(qExpense.user_id.eq(user_id));                 
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                switch (key) {
                    case "amount" -> predicate = predicate.and(qExpense.amount.eq(Double.valueOf(value)));
                    case "description" -> predicate = predicate.and(qExpense.description.containsIgnoreCase(value));
                    case "date" -> predicate = predicate.and(qExpense.expenseDate.eq(LocalDate.parse(value)));
                    case "expense_group" -> predicate = predicate.and(qExpense.expense_group_id.eq(Long.valueOf(value)));
                }
            }
        Page<Expense> expenses = expenseRepository.findAll(predicate, pageable);
        return expenses.map(expense -> mapper.map(expense, getTypeOfDTO()));
    }

    public Iterable<ExpenseDTO> findAll(){
        Long user_id = tokenUtils.getUserIdFromToken();
        List<Expense> incomes = expenseRepository.findByUser_id(user_id);
        return incomes.stream().map(income -> mapper.map(income, getTypeOfDTO()))
                        .collect(Collectors.toList());
    }

}
