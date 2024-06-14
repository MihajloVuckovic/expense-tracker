/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.exceptions;

import java.util.Date;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    
    return message;
  }

  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public ErrorMessage unauthenticatedException(AuthenticationException ex, WebRequest request){
    ErrorMessage message = new ErrorMessage(
        HttpStatus.UNAUTHORIZED.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));

    return message;
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(value = HttpStatus.FORBIDDEN)
  public ErrorMessage unauthorizedException(AccessDeniedException ex, WebRequest request){
    ErrorMessage message = new ErrorMessage(
        HttpStatus.FORBIDDEN.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));

    return message;
  }
  
  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    
    return message;
  }

  @ExceptionHandler(value = WebClientResponseException.class)
    public ErrorMessage handleStrapiForbidden(WebClientResponseException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
          ex.getStatusCode().value(),
          new Date(),
          ex.getMessage(),
          request.getDescription(false));
        return message;
    }

}
