/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.ExpenseDTO;
import com.demo.expense_tracker.dto.IncomeDTO;
import com.demo.expense_tracker.dto.ReminderDTO;
import com.demo.expense_tracker.model.Expense;
import com.demo.expense_tracker.model.Income;
import com.demo.expense_tracker.model.Reminder;
import com.demo.expense_tracker.model.User;
import com.demo.expense_tracker.repositories.ExpenseRepository;
import com.demo.expense_tracker.repositories.IncomeRepository;
import com.demo.expense_tracker.repositories.ReminderRepository;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class DashboardService {
    
    private ModelMapper mapper;

    public DashboardService(){
        this.mapper= new ModelMapper();
    }

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    public ReminderDTO getReminderDetails(){
        Long user_id = getUserIdFromToken();
        Reminder activeReminder = reminderRepository.findByUser_idAndActive(user_id,true);
        ReminderDTO activeReminderDTO = new ReminderDTO();
        mapper.map(activeReminder, activeReminderDTO);
        return activeReminderDTO;
    }

    public Double getTotalAmount() {
        Long user_id = getUserIdFromToken();
        Double totalExpenses = expenseRepository.findByUser_id(user_id)
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();
        
        Double totalIncome = incomeRepository.findByUser_id(user_id)
                .stream()
                .mapToDouble(Income::getAmount)
                .sum();
        
        return totalIncome - totalExpenses;
    }

    public List<ExpenseDTO> getLast5Expenses() {
        Long user_id = getUserIdFromToken();
        List<ExpenseDTO> expenseDTOs = new ArrayList<>();
        List<Expense> expenses = expenseRepository.findTop5ByUser_idOrderByExpenseDateDesc(user_id);
        for(Expense expense : expenses){
            ExpenseDTO expenseDTO = new ExpenseDTO();
            mapper.map(expense, expenseDTO);
            expenseDTOs.add(expenseDTO);
        }
        return expenseDTOs;

    }

    public List<IncomeDTO> getLast5Incomes() {
        Long user_id = getUserIdFromToken();
        List<IncomeDTO> incomeDTOs = new ArrayList<>();
        List<Income> incomes = incomeRepository.findTop5ByUser_idOrderByIncomeDateDesc(user_id);
        for(Income income : incomes){
            IncomeDTO incomeDTO = new IncomeDTO();
            mapper.map(income, incomeDTO);
            incomeDTOs.add(incomeDTO);
        }
        return incomeDTOs;
    }

    private Long getUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return ((User) userDetails).getId();
        }
        return null;
    }
}
