/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.demo.expense_tracker.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.expense_tracker.dto.ReminderDTO;
import com.demo.expense_tracker.model.Reminder;

/**
 *
 * @author mihajlo.vuckovic
 */
@Repository
public interface ReminderRepository extends GenericRepository<Reminder, ReminderDTO, Long>{
    List<Reminder> findByReminderDay(LocalDate date);
}
