/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.expense_tracker.dto.ReminderDTO;
import com.demo.expense_tracker.model.Reminder;
import com.demo.expense_tracker.repositories.ReminderRepository;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class ReminderService extends GenericServiceImpl<Reminder, ReminderDTO, Long> {
    @Autowired
    public ReminderService(ReminderRepository reminderRepository){
        super(reminderRepository);
    }
    @Override
    protected Class<ReminderDTO> getTypeOfDTO() {
        return ReminderDTO.class;
    }


}
