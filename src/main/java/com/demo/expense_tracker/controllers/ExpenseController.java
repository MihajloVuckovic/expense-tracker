/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.expense_tracker.dto.ExpenseDTO;
import com.demo.expense_tracker.model.Expense;
import com.demo.expense_tracker.pdf_generator.EmailService;
import com.demo.expense_tracker.pdf_generator.PDFGenerator;
import com.demo.expense_tracker.services.ExpenseService;
import com.demo.expense_tracker.utils.TokenUtils;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/dashboard/expenses")
public class ExpenseController extends GenericController<Expense, ExpenseDTO, Long> {
    private TokenUtils tokenUtils;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    public ExpenseController(ExpenseService expenseService){
        super(expenseService);
        this.tokenUtils = new TokenUtils();
    }

    @GetMapping(value="/export/pdf", produces=MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] exportToPdf() throws IOException{
        Iterable<ExpenseDTO> expenses = expenseService.findAll();
        ByteArrayOutputStream pdfOutputStream = PDFGenerator.generatePdf(expenses, ExpenseDTO.class);
        byte[] pdfBytes = pdfOutputStream.toByteArray();

        return pdfBytes;
    }

    @GetMapping("/export/email")
    public ResponseEntity<String> exportToEmail() {
        Iterable<ExpenseDTO> expenses = expenseService.findAll();

        ByteArrayOutputStream pdfOutputStream = PDFGenerator.generatePdf(expenses, ExpenseDTO.class);

        String recipientEmail = tokenUtils.getUserEmailFromToken();
        emailService.sendEmailWithAttachment(
                recipientEmail,
                "Expenses PDF",
                "Please find the attached PDF file containing the expenses list.",
                pdfOutputStream,
                "expense.pdf"
        );

        return new ResponseEntity<>("Email sent to " + recipientEmail, HttpStatus.OK);
    }

    
}
