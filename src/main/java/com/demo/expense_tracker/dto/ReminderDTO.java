/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.demo.expense_tracker.model.ReminderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author mihajlo.vuckovic
 */
@Getter
@Setter
@NoArgsConstructor
public class ReminderDTO implements Serializable{
    Long id;
    boolean active;
    LocalDate reminderDay;
    ReminderType type;
    Long user_id;
}
