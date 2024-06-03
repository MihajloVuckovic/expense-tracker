/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public Iterable<DTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> findById(@PathVariable ID id) {
        DTO dto = service.findById(id);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<DTO> update(@RequestBody final DTO dto, @PathVariable("id") final ID id) {
        if (service.existsById(id)) {
            service.update(dto, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("")
    public ResponseEntity<String> create(@RequestBody T t){
        T createdEntity = service.save(t);
        return new ResponseEntity<>("Entitet je kreiran!",HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable ID id){
        if(!service.existsById(id)){
            return new ResponseEntity<>("Trazeni entitet nije pronadjen!",HttpStatus.NOT_FOUND);
        }
        service.remove(id);
        return new ResponseEntity<>("Entitet uspesno obrisan!",HttpStatus.OK);
    }
}
