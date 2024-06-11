/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author mihajlo.vuckovic
 */
public interface GenericService<T,DTO,ID> {
    Page<DTO> findAll(Pageable pageable, Map<String, String> map);
    DTO findById(ID id);
    boolean existsById(ID id);
    T save(T t);
    void update(DTO dto, ID id);
    void remove(ID id);
    }

