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

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.ReminderDTO;
import com.demo.expense_tracker.model.QReminder;
import com.demo.expense_tracker.model.Reminder;
import com.demo.expense_tracker.model.ReminderType;
import com.demo.expense_tracker.repositories.ReminderRepository;

import com.demo.expense_tracker.utils.TokenUtils;
import com.querydsl.core.types.dsl.BooleanExpression;



/**
 *
 * @author mihajlo.vuckovic
 */
@Service

public class ReminderService extends GenericServiceImpl<Reminder, ReminderDTO, Long> {
    
    private final TokenUtils tokenUtils;
       
    @Autowired
    private ReminderRepository reminderRepository;

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
