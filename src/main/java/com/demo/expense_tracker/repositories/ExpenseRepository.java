/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.demo.expense_tracker.repositories;


import org.springframework.stereotype.Repository;

import com.demo.expense_tracker.dto.ExpenseDTO;
import com.demo.expense_tracker.model.Expense;

/**
 *
 * @author mihajlo.vuckovic
 */
@Repository
public interface ExpenseRepository extends GenericRepository<Expense, ExpenseDTO, Long> {

}