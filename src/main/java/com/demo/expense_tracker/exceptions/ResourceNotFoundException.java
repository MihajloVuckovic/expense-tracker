/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.exceptions;

/**
 *
 * @author mihajlo.vuckovic
 */
public class ResourceNotFoundException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
