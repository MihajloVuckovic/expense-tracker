/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.expense_tracker.dto.IncomeGroupDTO;
import com.demo.expense_tracker.model.IncomeGroup;
import com.demo.expense_tracker.services.IncomeGroupService;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/income-groups")
public class IncomeGroupController extends GenericController<IncomeGroup, IncomeGroupDTO, Long>{
    @Autowired
    public IncomeGroupController(IncomeGroupService incomeGroupService){
        super(incomeGroupService);
    }
}
