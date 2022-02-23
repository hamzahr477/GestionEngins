package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Critere;
import com.marsamaroc.gestionengins.repository.CritereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CritereServiceImp implements CritereService {
    @Autowired
    CritereRepository critereRepository;
    @Override
    public Critere getById(Long id) {
        return critereRepository.getById(id) ;
    }
}
