/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.exceptions;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

/**
 *
 * @author mihajlo.vuckovic
 */
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler{

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        System.err.println("Uncaught Exception occurred in async method: " + method.getName());
        System.err.println("Exception message: " + ex.getMessage());
    }

}
