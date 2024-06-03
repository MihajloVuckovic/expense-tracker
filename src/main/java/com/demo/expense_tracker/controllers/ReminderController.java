/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.expense_tracker.dto.ReminderDTO;
import com.demo.expense_tracker.model.Reminder;
import com.demo.expense_tracker.services.ReminderService;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/reminders")
public class ReminderController extends GenericController<Reminder, ReminderDTO, Long>{
    @Autowired
    public ReminderController(ReminderService reminderService){
        super(reminderService);
    }
}
