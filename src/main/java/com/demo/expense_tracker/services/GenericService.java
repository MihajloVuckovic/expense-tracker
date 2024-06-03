/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.demo.expense_tracker.services;

/**
 *
 * @author mihajlo.vuckovic
 */
public interface GenericService<T,DTO,ID> {
    Iterable<DTO> findAll();
    DTO findById(ID id);
    boolean existsById(ID id);
    T save(T t);
    void update(DTO dto, ID id);
    void remove(ID id);
    }

