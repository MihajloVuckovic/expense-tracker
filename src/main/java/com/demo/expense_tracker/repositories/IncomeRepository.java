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

import com.demo.expense_tracker.dto.IncomeDTO;
import com.demo.expense_tracker.model.Income;
import com.demo.expense_tracker.model.IncomeGroup;

/**
 *
 * @author mihajlo.vuckovic
 */
@Repository
public interface IncomeRepository extends GenericRepository<Income, IncomeDTO, Long> {

    public List<Income> findTop5ByUser_idOrderByIncomeDateDesc(Long user_id);
    public Page<Income> findByUser_id(Long user_id, Pageable pageable);
    public List<Income> findByUser_id(Long user_id);
    Page<Income> findByUser_idAndAmount(Long user_id, Double amount, Pageable pageable);
    Page<Income> findByUser_idAndDescriptionContaining(Long user_id, String description, Pageable pageable);
    Page<Income> findByUser_idAndIncomeDate(Long user_id, LocalDate incomeDate, Pageable pageable);
    Page<Income> findByUser_idAndAmountAndDescriptionContaining(Long user_id, Double amount, String description, Pageable pageable);
    Page<Income> findByUser_idAndAmountAndIncomeDate(Long user_id, Double amount, LocalDate date, Pageable pageable);
    Page<Income> findByUser_idAndIncomeDateAndDescriptionContaining(Long user_id, LocalDate date, String description, Pageable pageable);
    Page<Income> findByUser_idAndIncomeDateAndDescriptionContainingAndAmount(Long user_id, LocalDate date, String description, Double amount, Pageable pageable);

    List<Income> findTop5ByIncomeGroupAndUser_idOrderByIncomeDateDesc(IncomeGroup incomeGroup, Long user_id);
}
