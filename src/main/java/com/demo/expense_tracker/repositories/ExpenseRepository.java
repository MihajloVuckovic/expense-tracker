/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.demo.expense_tracker.repositories;


import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.expense_tracker.dto.ExpenseDTO;
import com.demo.expense_tracker.model.Expense;

/**
 *
 * @author mihajlo.vuckovic
 */
@Repository
public interface ExpenseRepository extends GenericRepository<Expense, ExpenseDTO, Long> {
    List<Expense> findByExpenseDateBetweenAndUser_Id(LocalDate startDate, LocalDate endDate, Long user_id);  

    public List<Expense> findTop5ByUser_idOrderByExpenseDateDesc(Long user_id);
    public List<Expense> findByUser_id(Long user_id);
}
