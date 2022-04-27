package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Critere;
import com.marsamaroc.gestionengins.exception.ResourceNotFoundException;
import com.marsamaroc.gestionengins.repository.CritereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CritereService   {
    @Autowired
    CritereRepository critereRepository;
    public Critere getById(Long id) {
        return critereRepository.getById(id) ;
    }

    public List<Critere> findAllCriteres() {
        return critereRepository.findAll();
    }

    public Critere removeCritere(Long id) throws ResourceNotFoundException {
        Critere critereOld = critereRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Critere not found for id :: "+id));
        critereOld.setActive(false);
        return critereOld;
    }
}
