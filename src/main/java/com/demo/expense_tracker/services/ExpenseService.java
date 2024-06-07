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

import com.demo.expense_tracker.dto.ExpenseDTO;
import com.demo.expense_tracker.model.Expense;
import com.demo.expense_tracker.repositories.ExpenseRepository;
import com.demo.expense_tracker.utils.TokenUtils;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class ExpenseService extends GenericServiceImpl<Expense, ExpenseDTO, Long>{

    private final TokenUtils tokenUtils;

    private final ModelMapper mapper;

    private ExpenseRepository expenseRepository;

    @Autowired
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
    public Expense save(Expense t) {
        Long user_id = tokenUtils.getUserIdFromToken();
        t.setUser_id(user_id);
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
    public Page<ExpenseDTO> findAll(Pageable pageable) {
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Expense> expenses = expenseRepository.findByUser_id(user_id, pageable);
        return expenses.map(expense -> mapper.map(expense, getTypeOfDTO()));
    }

    public Iterable<ExpenseDTO> findAll(){
        Long user_id = tokenUtils.getUserIdFromToken();
        List<Expense> incomes = expenseRepository.findByUser_id(user_id);
        return incomes.stream().map(income -> mapper.map(income, getTypeOfDTO()))
                        .collect(Collectors.toList());
    }
}
