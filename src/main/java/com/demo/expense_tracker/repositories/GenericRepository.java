/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.demo.expense_tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author mihajlo.vuckovic
 */
@NoRepositoryBean
public interface GenericRepository<T,DTO,ID> extends JpaRepository<T,ID> {
}

