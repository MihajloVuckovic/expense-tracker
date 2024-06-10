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

import com.demo.expense_tracker.dto.IncomeGroupDTO;
import com.demo.expense_tracker.model.IncomeGroup;
import com.demo.expense_tracker.services.IncomeGroupService;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/dashboard/income-groups")
public class IncomeGroupController extends GenericController<IncomeGroup, IncomeGroupDTO, Long>{
    @Autowired
    public IncomeGroupController(IncomeGroupService incomeGroupService){
        super(incomeGroupService);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<String> delete(Long id) {
        return super.delete(id);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<String> create(IncomeGroup t) {
        return super.create(t);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<IncomeGroupDTO> update(IncomeGroupDTO dto, Long id) {
        return super.update(dto, id);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<IncomeGroupDTO> findById(Long id) {
        return super.findById(id);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public Page<IncomeGroupDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> allParams) {
        return super.findAll(page, size, sortBy, sortDir, allParams);
    }
}
