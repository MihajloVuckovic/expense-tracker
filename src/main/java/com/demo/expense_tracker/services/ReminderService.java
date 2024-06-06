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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.ReminderDTO;
import com.demo.expense_tracker.model.Expense;
import com.demo.expense_tracker.model.Reminder;
import com.demo.expense_tracker.model.ReminderType;
import com.demo.expense_tracker.model.User;
import com.demo.expense_tracker.repositories.ExpenseRepository;
import com.demo.expense_tracker.repositories.ReminderRepository;
import com.demo.expense_tracker.repositories.UserRepository;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class ReminderService extends GenericServiceImpl<Reminder, ReminderDTO, Long> {
    

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    public ReminderService(ReminderRepository reminderRepository){
        super(reminderRepository);
    }

    @Override
    protected Class<ReminderDTO> getTypeOfDTO() {
        return ReminderDTO.class;
    }

    @Scheduled(cron="0 0 12 * * ?")
    public void sendReminders(){
        LocalDate now = LocalDate.now();
        List<Reminder> remindersLeft = reminderRepository.findByReminderDay(now);
        for(Reminder reminder : remindersLeft){
            if(reminder.isActive()){
            sendEmail(reminder);
            updateReminder(reminder);
            }
        }
    }

    private void sendEmail(Reminder reminder){
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
    private void updateReminder(Reminder reminder){
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

    @Override
    public Reminder save(Reminder t) {
        if(ReminderType.WEEKLY.equals(t.getType())){
            t.setReminderDay(LocalDate.now().plusWeeks(1));
        } else if(ReminderType.MONTHLY.equals(t.getType())){
            t.setReminderDay(LocalDate.now().plusMonths(1));
        }
        return super.save(t);
    }
}
