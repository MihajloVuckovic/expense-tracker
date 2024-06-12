/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.ReminderDTO;
import com.demo.expense_tracker.model.QReminder;
import com.demo.expense_tracker.model.Reminder;
import com.demo.expense_tracker.model.ReminderType;
import com.demo.expense_tracker.repositories.ExpenseRepository;
import com.demo.expense_tracker.repositories.ReminderRepository;
import com.demo.expense_tracker.repositories.UserRepository;
import com.demo.expense_tracker.utils.TokenUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
@Slf4j
public class ReminderService extends GenericServiceImpl<Reminder, ReminderDTO, Long> {
    
    private final TokenUtils tokenUtils;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ExpenseRepository expenseRepository;

    private final ModelMapper mapper;

    @Autowired
    private ReminderSchedule reminderSchedule;

    
    public ReminderService(ReminderRepository reminderRepository){
        super(reminderRepository);
        this.tokenUtils= new TokenUtils();
        this.mapper = new ModelMapper();
    }

    @Override
    protected Class<ReminderDTO> getTypeOfDTO() {
        return ReminderDTO.class;
    }

    @Override 
    protected String entityName(){
        return Reminder.class.getSimpleName();
    }

    
    @Scheduled(cron="0 0 12 * * ?")   
    public void sendReminders(){
        LocalDate now = LocalDate.now();
        List<Reminder> remindersLeft = reminderRepository.findByReminderDay(now);
        for(Reminder reminder : remindersLeft){
            if(reminder.isActive()){
                reminderSchedule.sendEmail(reminder);
                reminderSchedule.updateReminder(reminder);
            }
        }
    }

    // @Async("reminderExecutor")
    // public void sendEmail(Reminder reminder){
    //     Long user_id = reminder.getUser_id();
    //     Optional<User> optionalUser = userRepo.findById(user_id);
    //     User user = optionalUser.get();
    //     String email = user.getEmail();

    //     double totalExpense = fetchExpense(user_id, reminder);

    //     SimpleMailMessage message = new SimpleMailMessage();
    //     message.setTo(email);
    //     message.setSubject("Expenses summary");
    //     message.setText("You have spent: " + totalExpense + "rsd");

    //     mailSender.send(message);
        
    // }
    // @Async("reminderExecutor")
    // public void updateReminder(Reminder reminder){
    //     if(ReminderType.WEEKLY.equals(reminder.getType())){
    //         reminder.setReminderDay(reminder.getReminderDay().plusWeeks(1));
    //     } else if(ReminderType.MONTHLY.equals(reminder.getType())){
    //         reminder.setReminderDay(reminder.getReminderDay().plusMonths(1));
    //     }
    //     reminderRepository.save(reminder);
    // }

    // private double fetchExpense(Long user_id, Reminder reminder){
    //     LocalDate now = LocalDate.now();
    //     LocalDate startDate;
    //     ReminderType type = reminder.getType();

    //     if(null == type){
    //         throw new IllegalArgumentException("Invalid type of reminder");
    //     }else switch (type) {
    //         case WEEKLY -> startDate = now.minusWeeks(1);
    //         case MONTHLY -> startDate = now.minusMonths(1);
    //         default -> throw new IllegalArgumentException("Invalid type of reminder");
    //     }

    //     List<Expense> expenses = expenseRepository.findByExpenseDateBetweenAndUser_Id(startDate, now, user_id);
    //     System.out.print(expenses);
    //     return expenses.stream().mapToDouble(Expense::getAmount).sum();
    // }

    @Override
    public Reminder save(Reminder t) {
        Long user_id = tokenUtils.getUserIdFromToken();
        Reminder existingReminder = reminderRepository.findByUser_idAndActive(user_id, true);
        if(existingReminder != null && t.isActive()){
            t.setActive(false);
        }
        if(ReminderType.WEEKLY.equals(t.getType())){
            t.setReminderDay(LocalDate.now().plusWeeks(1));
        } else if(ReminderType.MONTHLY.equals(t.getType())){
            t.setReminderDay(LocalDate.now().plusMonths(1));
        }
        t.setUser_id(user_id);
        return super.save(t);
    }

    @Override
    public Page<ReminderDTO> findAll(Pageable pageable, Map<String, String> map) {
        QReminder qReminder = QReminder.reminder;
        BooleanExpression predicate = qReminder.isNotNull();
        Long user_id = tokenUtils.getUserIdFromToken();
        predicate = predicate.and(qReminder.user_id.eq(user_id));                 
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                switch (key) {
                    case "type" -> predicate = predicate.and(qReminder.type.eq(ReminderType.valueOf(value)));
                    case "active" -> predicate = predicate.and(qReminder.active.eq(Boolean.valueOf(value)));
                    case "reminder_day" -> predicate = predicate.and(qReminder.reminderDay.eq(LocalDate.parse(value)));
                }
            }
        Page<Reminder> reminders = reminderRepository.findAll(predicate, pageable);
        return reminders.map(reminder -> mapper.map(reminder, getTypeOfDTO()));
    }

    

    
}
