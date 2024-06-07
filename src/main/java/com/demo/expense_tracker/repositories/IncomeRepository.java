/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.demo.expense_tracker.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.demo.expense_tracker.dto.IncomeDTO;
import com.demo.expense_tracker.model.Income;

/**
 *
 * @author mihajlo.vuckovic
 */
@Repository
public interface IncomeRepository extends GenericRepository<Income, IncomeDTO, Long> {

    public List<Income> findTop5ByUser_idOrderByIncomeDateDesc(Long user_id);
    public Page<Income> findByUser_id(Long user_id, Pageable pageable);
    public List<Income> findByUser_id(Long user_id);

}
