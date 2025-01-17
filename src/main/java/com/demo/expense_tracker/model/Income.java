/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.model;

import java.time.LocalDate;

import jakarta.persistence.*;
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
@Table(name = "Income")
public class Income {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    private String description;
    @Column
    private double amount;
    @Column(name="income_date")
    private LocalDate incomeDate;
    @ManyToOne
    @JoinColumn(name="income_group_id", referencedColumnName="id", insertable=false, updatable=false)
    private IncomeGroup incomeGroup;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id", insertable=false, updatable=false)
    private User user;

    private Long income_group_id;

    private Long user_id;
}
