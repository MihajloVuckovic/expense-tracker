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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Expense")
public class Expense {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    private String description;
    @Column
    private Double amount;
    
    @Column(name="expense_date")
    private LocalDate expenseDate;

    @ManyToOne
    @JoinColumn(name="expense_group_id", referencedColumnName="id", insertable=false,updatable=false, nullable=false)
    private ExpenseGroup expenseGroup;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id", insertable=false, updatable=false)
    private User user;

    private Long expense_group_id;

    private Long user_id;

}
