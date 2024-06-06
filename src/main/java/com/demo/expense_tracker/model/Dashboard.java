/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.model;

import java.util.ArrayList;
import java.util.List;

import com.demo.expense_tracker.dto.ExpenseDTO;
import com.demo.expense_tracker.dto.IncomeDTO;
import com.demo.expense_tracker.dto.ReminderDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author mihajlo.vuckovic
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard {
    private List<IncomeDTO> incomes = new ArrayList<>();
    private List<ExpenseDTO> expenses = new ArrayList<>();
    private Double totalBalance;
    private ReminderDTO reminder;
}
