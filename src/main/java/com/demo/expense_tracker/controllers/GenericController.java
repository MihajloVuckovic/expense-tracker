/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.expense_tracker.services.GenericService;

/**
 *
 * @author mihajlo.vuckovic
 */
public class GenericController<T,DTO, ID> {
    private final GenericService<T,DTO, ID> service;
    protected GenericController(GenericService<T,DTO,ID> service){
        this.service=service;
    }

    @GetMapping("")
    public Page<DTO> findAll(
                            @RequestParam(defaultValue="0") int page, 
                            @RequestParam(defaultValue="10") int size,
                            @RequestParam(defaultValue="id") String sortBy,
                            @RequestParam(defaultValue="asc") String sortDir,
                            @RequestParam(required=false) Map<String, String> allParams){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> findById(@PathVariable ID id) {
        DTO dto = service.findById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<DTO> update(@RequestBody final DTO dto, @PathVariable("id") final ID id) {
            service.update(dto, id);
            return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<T> create(@RequestBody T t){
        service.save(t);
        return new ResponseEntity<>(t,HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable ID id){
        service.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
