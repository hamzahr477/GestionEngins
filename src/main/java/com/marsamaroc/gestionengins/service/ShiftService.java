package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Shift;
import com.marsamaroc.gestionengins.exception.ResourceNotFoundException;

import java.util.List;

public interface ShiftService {
    Shift save(Shift shift);
    Shift update(Shift shift) throws ResourceNotFoundException;
    List<Shift> findAll();
    Shift   getById(Long id) throws ResourceNotFoundException;
    Shift getByCodeShift(String codeShift)throws ResourceNotFoundException;

    Shift saveIfNotExist(Shift shift);
}
