/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import com.demo.expense_tracker.repositories.GenericRepository;

/**
 *
 * @author mihajlo.vuckovic
 */
public abstract class GenericServiceImpl<T,DTO,ID> implements GenericService<T,DTO,ID> {
    private final GenericRepository<T,DTO,ID> genericRepository;
    private final ModelMapper mapper;
    protected abstract Class<DTO> getTypeOfDTO();
    public GenericServiceImpl(GenericRepository<T,DTO,ID> genericRepository){
        this.genericRepository=genericRepository;
        mapper = new ModelMapper();
    }
    @Override
    public Iterable<DTO> findAll() {
        List<T> entities = genericRepository.findAll(Sort.by("id"));
        return entities.stream()
                .map(entity -> mapper.map(entity, getTypeOfDTO()))
                .collect(Collectors.toList());
    }
    @Override
    public DTO findById(ID id) {
        Optional<T> t = genericRepository.findById(id);
        return t.map(value -> mapper.map(value, getTypeOfDTO())).orElse(null);
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
                .orElseThrow(null);
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        mapper.map(dto, t);
        genericRepository.save(t);
    }
    @Override
    public void remove(ID id) {
        genericRepository.deleteById(id);
    }

}
