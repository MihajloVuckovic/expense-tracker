/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.util.Map;
import java.util.Optional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.demo.expense_tracker.exceptions.ResourceNotFoundException;
import com.demo.expense_tracker.repositories.GenericRepository;

/**
 *
 * @author mihajlo.vuckovic
 */
public abstract class GenericServiceImpl<T,DTO,ID> implements GenericService<T,DTO,ID> {
    private final GenericRepository<T,DTO,ID> genericRepository;
    private final ModelMapper mapper;
    protected abstract Class<DTO> getTypeOfDTO();
    protected abstract String entityName();
    public GenericServiceImpl(GenericRepository<T,DTO,ID> genericRepository){
        this.genericRepository=genericRepository;
        mapper = new ModelMapper();
    }
    @Override
    public Page<DTO> findAll(Pageable pageable, Map<String, String> map) {
        Page<T> entities = genericRepository.findAll(pageable);
        return entities.map(entity -> mapper.map(entity, getTypeOfDTO()));
    }
    @Override
    public DTO findById(ID id) {
        Optional<T> t = genericRepository.findById(id);
        return t.map(value -> mapper.map(value, getTypeOfDTO())).orElseThrow(() -> new ResourceNotFoundException(entityName() + " with id " + id + " not found"));
    }
    @Override
    public boolean existsById(ID id) {
        return genericRepository.existsById(id);
    }
    @Override
    public T save(T t) {
        return genericRepository.save(t);
    }
    @Override
    public void update(final DTO dto, final ID id) {
        final T t = genericRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName() + " with id " + id + " not found"));
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        mapper.map(dto, t);
        genericRepository.save(t);
    }
    @Override
    public void remove(ID id) {
        genericRepository.deleteById(id);
    }

}
