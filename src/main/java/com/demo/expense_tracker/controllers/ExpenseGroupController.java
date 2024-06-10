/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.expense_tracker.dto.ExpenseGroupDTO;
import com.demo.expense_tracker.model.ExpenseGroup;
import com.demo.expense_tracker.services.ExpenseGroupService;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/dashboard/expense-groups")
public class ExpenseGroupController extends GenericController<ExpenseGroup, ExpenseGroupDTO, Long>{
    @Autowired
    public ExpenseGroupController(ExpenseGroupService expenseGroupService){
        super(expenseGroupService);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<String> delete(Long id) {
        return super.delete(id);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<ExpenseGroup> create(ExpenseGroup t) {
        return super.create(t);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<ExpenseGroupDTO> update(ExpenseGroupDTO dto, Long id) {
        return super.update(dto, id);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<ExpenseGroupDTO> findById(Long id) {
        return super.findById(id);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public Page<ExpenseGroupDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> allParams) {
        return super.findAll(page, size, sortBy, sortDir, allParams);
    }
}
