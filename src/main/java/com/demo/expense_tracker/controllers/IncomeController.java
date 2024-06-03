/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.expense_tracker.dto.IncomeDTO;
import com.demo.expense_tracker.model.Income;
import com.demo.expense_tracker.services.IncomeService;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/incomes")
public class IncomeController extends GenericController<Income, IncomeDTO, Long>{
    @Autowired
    public IncomeController(IncomeService incomeService){
        super(incomeService);
    }
}
