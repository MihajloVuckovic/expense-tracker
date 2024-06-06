/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.expense_tracker.model.Dashboard;
import com.demo.expense_tracker.services.DashboardService;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("")
    public ResponseEntity<Dashboard> dashboardInfo(){
        Dashboard dashboard = new Dashboard();
        dashboard.setExpenses(dashboardService.getLast5Expenses());
        dashboard.setIncomes(dashboardService.getLast5Incomes());
        dashboard.setTotalBalance(dashboardService.getTotalAmount());
        dashboard.setReminder(dashboardService.getReminderDetails());

        return new ResponseEntity<>(dashboard, HttpStatus.OK);
    }
}
