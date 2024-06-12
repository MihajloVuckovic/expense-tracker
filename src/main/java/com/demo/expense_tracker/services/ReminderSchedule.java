/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.demo.expense_tracker.model.Expense;
import com.demo.expense_tracker.model.Reminder;
import com.demo.expense_tracker.model.ReminderType;
import com.demo.expense_tracker.model.User;
import com.demo.expense_tracker.repositories.ExpenseRepository;
import com.demo.expense_tracker.repositories.ReminderRepository;
import com.demo.expense_tracker.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author mihajlo.vuckovic
 */
@Component
@Slf4j
public class ReminderSchedule {

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ReminderRepository reminderRepository;


    @Async("reminderExecutor")
    public void sendEmail(Reminder reminder){
        log.info("Sending reminder");
        Long user_id = reminder.getUser_id();
        Optional<User> optionalUser = userRepo.findById(user_id);
        User user = optionalUser.get();
        String email = user.getEmail();

        double totalExpense = fetchExpense(user_id, reminder);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Expenses summary");
        message.setText("You have spent: " + totalExpense + "rsd");

        mailSender.send(message);
        
    }
    @Async("reminderExecutor")
    public void updateReminder(Reminder reminder){
        log.info("Updating reminder");
        if(ReminderType.WEEKLY.equals(reminder.getType())){
            reminder.setReminderDay(reminder.getReminderDay().plusWeeks(1));
        } else if(ReminderType.MONTHLY.equals(reminder.getType())){
            reminder.setReminderDay(reminder.getReminderDay().plusMonths(1));
        }
        reminderRepository.save(reminder);
    }

    private double fetchExpense(Long user_id, Reminder reminder){
        LocalDate now = LocalDate.now();
        LocalDate startDate;
        ReminderType type = reminder.getType();

        if(null == type){
            throw new IllegalArgumentException("Invalid type of reminder");
        }else switch (type) {
            case WEEKLY -> startDate = now.minusWeeks(1);
            case MONTHLY -> startDate = now.minusMonths(1);
            default -> throw new IllegalArgumentException("Invalid type of reminder");
        }

        List<Expense> expenses = expenseRepository.findByExpenseDateBetweenAndUser_Id(startDate, now, user_id);
        System.out.print(expenses);
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }
}
