/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.demo.expense_tracker.repositories;


import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.demo.expense_tracker.dto.IncomeDTO;
import com.demo.expense_tracker.model.Income;
import com.demo.expense_tracker.model.IncomeGroup;

/**
 *
 * @author mihajlo.vuckovic
 */
@Repository
public interface IncomeRepository extends GenericRepository<Income, IncomeDTO, Long>,QuerydslPredicateExecutor<Income> {

    List<Income> findTop5ByUser_idOrderByIncomeDateDesc(Long user_id);
    List<Income> findByUser_id(Long user_id);
    List<Income> findTop5ByIncomeGroupAndUser_idOrderByIncomeDateDesc(IncomeGroup incomeGroup, Long user_id);
}
