/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import com.demo.expense_tracker.dto.IncomeDTO;
import com.demo.expense_tracker.model.Income;
import com.demo.expense_tracker.pdf_generator.EmailService;
import com.demo.expense_tracker.pdf_generator.PDFGenerator;
import com.demo.expense_tracker.services.IncomeService;
import com.demo.expense_tracker.utils.TokenUtils;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/dashboard/incomes")
public class IncomeController extends GenericController<Income, IncomeDTO, Long>{
    private final TokenUtils tokenUtils;
    @Autowired
    private EmailService emailService;
    @Autowired
    private IncomeService incomeService;

    public IncomeController(IncomeService incomeService){
        super(incomeService);
        this.tokenUtils= new TokenUtils();
    }

    @GetMapping(value = "/export/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public  @ResponseBody byte[] exportToPdf() throws IOException{
        Iterable<IncomeDTO> incomes = incomeService.findAll();
        
        ByteArrayOutputStream pdfOutputStream = PDFGenerator.generatePdf(incomes, IncomeDTO.class);

        assert pdfOutputStream != null;
        return pdfOutputStream.toByteArray();
    }

    @GetMapping("/export/email")
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<String> exportToEmail() {
        Iterable<IncomeDTO> incomes = incomeService.findAll();

        ByteArrayOutputStream pdfOutputStream = PDFGenerator.generatePdf(incomes, IncomeDTO.class);

        String recipientEmail = tokenUtils.getUserEmailFromToken();
        assert pdfOutputStream != null;
        emailService.sendEmailWithAttachment(
                recipientEmail,
                "Incomes PDF",
                "Please find the attached PDF file containing the incomes list.",
                pdfOutputStream,
                "incomes.pdf"
        );

        return new ResponseEntity<>("Email sent to " + recipientEmail, HttpStatus.OK);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    @Parameters({
        @Parameter(name = "amount", description = "Income amount", schema = @Schema(type = "string")),
        @Parameter(name = "description", description = "Income description", schema = @Schema(type = "string")),
        @Parameter(name = "date", description = "Income date", schema = @Schema(type = "string")),
        @Parameter(name = "income_group", description = "Income group", schema = @Schema(type = "string"))
    })
    public Page<IncomeDTO> findAll(@RequestParam(defaultValue="0") int page, 
                                    @RequestParam(defaultValue="10") int size,
                                    @RequestParam(defaultValue="id") String sortBy,
                                    @RequestParam(defaultValue="asc") String sortDir,
                                    @RequestParam(required=false) Map<String, String> allParams) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return incomeService.findAll(pageable, allParams);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<String> delete(Long id) {
        return super.delete(id);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<Income> create(Income t) {
        return super.create(t);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<IncomeDTO> update(IncomeDTO dto, Long id) {
        return super.update(dto, id);
    }

    @Override
    @Secured({"ROLE_PREMIUM", "ROLE_STANDARD"})
    public ResponseEntity<IncomeDTO> findById(Long id) {
        return super.findById(id);
    }

    
}
