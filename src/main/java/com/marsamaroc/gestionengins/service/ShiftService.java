package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Shift;
import com.marsamaroc.gestionengins.exception.ResourceNotFoundException;
import com.marsamaroc.gestionengins.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShiftService  {

    @Autowired
    ShiftRepository shiftRepository;
    public Shift save(Shift shift) {
        return shiftRepository.save(shift);
    }

    public Shift update(Shift shift) throws ResourceNotFoundException {
        shiftRepository.findById(shift.getId()).orElseThrow(()->new ResourceNotFoundException("Shift not found for this id :: "+shift.getId()));
        return  shiftRepository.save(shift);
    }

    public List<Shift> findAll() {
        return shiftRepository.findAll();
    }

    public Shift getById(Long id) throws ResourceNotFoundException {
        return shiftRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Shift not found for this id :: "+id));

    }

    public Shift getByCodeShift(String codeShift) throws ResourceNotFoundException {
        return shiftRepository.findAllByCodeShift(codeShift).orElseThrow(()->new ResourceNotFoundException("Shift not found for this code :: "+codeShift));

    }

    public Shift saveIfNotExist(Shift shift) {
        if(shiftRepository.findAllByCodeShift(shift.getCodeShift()).orElse(null) == null)
            shift = shiftRepository.save(shift);
        return shift;
    }
}
