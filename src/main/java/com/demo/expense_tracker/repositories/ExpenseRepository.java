/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.demo.expense_tracker.repositories;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Expense> findByUser_id(Long user_id, Pageable pageable);
    public List<Expense> findByUser_id(Long user_id);
    Page<Expense> findByUser_idAndAmount(Long user_id, Double amount, Pageable pageable);
    Page<Expense> findByUser_idAndDescriptionContaining(Long user_id, String description, Pageable pageable);
    Page<Expense> findByUser_idAndExpenseDate(Long user_id, LocalDate date, Pageable pageable);
    Page<Expense> findByUser_idAndAmountAndDescriptionContaining(Long user_id, Double amount, String description, Pageable pageable);
    Page<Expense> findByUser_idAndAmountAndExpenseDate(Long user_id, Double amount, LocalDate date, Pageable pageable);
    Page<Expense> findByUser_idAndExpenseDateAndDescriptionContaining(Long user_id, LocalDate date, String description, Pageable pageable);
    Page<Expense> findByUser_idAndExpenseDateAndDescriptionContainingAndAmount(Long user_id, LocalDate date, String description, Double amount, Pageable pageable);
}
