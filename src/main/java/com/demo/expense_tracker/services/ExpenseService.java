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
    
    @Autowired
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

    public Page<ExpenseDTO> filterAmount(Pageable pageable, Double amount){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Expense> incomesAmount = expenseRepository.findByUser_idAndAmount(user_id, amount, pageable);
        return incomesAmount.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Page<ExpenseDTO> filterDescription(Pageable pageable, String description){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Expense> incomesDescription = expenseRepository.findByUser_idAndDescriptionContaining(user_id, description, pageable);
        return incomesDescription.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Page<ExpenseDTO> filterDate(Pageable pageable, LocalDate date){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Expense> incomesDate = expenseRepository.findByUser_idAndExpenseDate(user_id, date, pageable);
        return incomesDate.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Page<ExpenseDTO> filterAmountAndDescription(Pageable pageable, Double amount, String description){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Expense> incomesDate = expenseRepository.findByUser_idAndAmountAndDescriptionContaining(user_id, amount, description, pageable);
        return incomesDate.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Page<ExpenseDTO> filterAmountAndDate(Pageable pageable, Double amount, LocalDate date){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Expense> incomesDate = expenseRepository.findByUser_idAndAmountAndExpenseDate(user_id, amount, date, pageable);
        return incomesDate.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Page<ExpenseDTO> filterDateAndDescription(Pageable pageable, LocalDate date, String description){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Expense> incomesDate = expenseRepository.findByUser_idAndExpenseDateAndDescriptionContaining(user_id, date, description, pageable);
        return incomesDate.map(income -> mapper.map(income, getTypeOfDTO()));
    }

    public Page<ExpenseDTO> filterAmountAndDescriptionAndIncomeDate(Pageable pageable, Double amount, String description, LocalDate date){
        Long user_id = tokenUtils.getUserIdFromToken();
        Page<Expense> incomesDate = expenseRepository.findByUser_idAndExpenseDateAndDescriptionContainingAndAmount(user_id, date, description, amount, pageable);
        return incomesDate.map(income -> mapper.map(income, getTypeOfDTO()));
    }
}
