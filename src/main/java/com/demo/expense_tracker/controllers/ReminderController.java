/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.expense_tracker.dto.ReminderDTO;
import com.demo.expense_tracker.model.Reminder;
import com.demo.expense_tracker.services.ReminderService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/dashboard/reminders")
public class ReminderController extends GenericController<Reminder, ReminderDTO, Long>{
    
    public ReminderController(ReminderService reminderService){
        super(reminderService);
    }

    @Override
    @Secured("ROLE_PREMIUM")
    public ResponseEntity<String> delete(Long id) {
        return super.delete(id);
    }

    @Override
    @Secured("ROLE_PREMIUM")
    public ResponseEntity<Reminder> create(Reminder t) {
        return super.create(t);
    }

    @Override
    @Secured("ROLE_PREMIUM")
    public ResponseEntity<ReminderDTO> update(ReminderDTO dto, Long id) {
        return super.update(dto, id);
    }

    @Override
    @Secured("ROLE_PREMIUM")
    public ResponseEntity<ReminderDTO> findById(Long id) {
        return super.findById(id);
    }

    @Override
    @Parameters({
        @Parameter(name = "active", description = "Expense amount", schema = @Schema(type = "string")),
        @Parameter(name = "type", description = "Expense description", schema = @Schema(type = "string")),
        @Parameter(name = "reminder_date", description = "Expense date", schema = @Schema(type = "string")),
    })
    @Secured("ROLE_PREMIUM")
    public Page<ReminderDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> allParams) {
        return super.findAll(page, size, sortBy, sortDir, allParams);
    }
}
