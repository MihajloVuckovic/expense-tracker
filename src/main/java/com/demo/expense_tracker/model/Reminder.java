/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author mihajlo.vuckovic
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    private boolean active;
    @Column(name="reminder_day")
    private LocalDate reminderDay;
    @Enumerated(EnumType.STRING)
    private ReminderType type;
    @ManyToOne()
    @JoinColumn(name="user_id", referencedColumnName="id", insertable=false,updatable=false)
    private User user;

    private Long user_id;

}
