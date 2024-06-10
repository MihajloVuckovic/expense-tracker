/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final TokenUtils tokenUtils;
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
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public @ResponseBody byte[] exportToPdf() throws IOException{
        Iterable<ExpenseDTO> expenses = expenseService.findAll();
        ByteArrayOutputStream pdfOutputStream = PDFGenerator.generatePdf(expenses, ExpenseDTO.class);
        byte[] pdfBytes = pdfOutputStream.toByteArray();

        return pdfBytes;
    }

    @GetMapping("/export/email")
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
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

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public Page<ExpenseDTO> findAll(@RequestParam(defaultValue="0") int page, 
                                    @RequestParam(defaultValue="10") int size,
                                    @RequestParam(defaultValue="id") String sortBy,
                                    @RequestParam(defaultValue="asc") String sortDir,
                                    @RequestParam(required=false) Map<String, String> allParams) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Map<String,Object> filterParams = new HashMap<>();
        allParams.forEach((key, value)->{
            if (!key.equals("page") && !key.equals("size") && !key.equals("sortBy") && !key.equals("sortDir")) {
                filterParams.put(key, value);
            }
        });
        if(filterParams.containsKey("amount")){
            Double amountValue = Double.parseDouble(filterParams.get("amount").toString());
            return expenseService.filterAmount(pageable, amountValue);
        }else if(filterParams.containsKey("description")){
            String description = filterParams.get("description").toString();
            return expenseService.filterDescription(pageable, description);
        }else if(filterParams.containsKey("date")){
            LocalDate date = LocalDate.parse(filterParams.get("date").toString());
            return expenseService.filterDate(pageable, date);
        }else if(filterParams.containsKey("amount") && filterParams.containsKey("description")){
            Double amountValue = Double.parseDouble(filterParams.get("amount").toString());
            String description = filterParams.get("description").toString();
            return expenseService.filterAmountAndDescription(pageable, amountValue, description);
        }else if(filterParams.containsKey("amount")&& filterParams.containsKey("date")){
            Double amountValue = Double.parseDouble(filterParams.get("amount").toString());
            LocalDate date = LocalDate.parse(filterParams.get("date").toString());
            return expenseService.filterAmountAndDate(pageable, amountValue, date);
        }else if(filterParams.containsKey("date") && filterParams.containsKey("description")){
            LocalDate date = LocalDate.parse(filterParams.get("date").toString());
            String description = filterParams.get("description").toString();
            return expenseService.filterDateAndDescription(pageable, date, description);
        }else if(filterParams.containsKey("amount")&& filterParams.containsKey("date") && filterParams.containsKey("description")){
            Double amountValue = Double.parseDouble(filterParams.get("amount").toString());
            LocalDate date = LocalDate.parse(filterParams.get("date").toString());
            String description = filterParams.get("description").toString();
            return expenseService.filterAmountAndDescriptionAndIncomeDate(pageable, amountValue, description, date);
        }
        return expenseService.findAll(pageable);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<String> delete(Long id) {
        return super.delete(id);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<String> create(Expense t) {
        return super.create(t);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<ExpenseDTO> update(ExpenseDTO dto, Long id) {
        return super.update(dto, id);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<ExpenseDTO> findById(Long id) {
        return super.findById(id);
    }

    
}
