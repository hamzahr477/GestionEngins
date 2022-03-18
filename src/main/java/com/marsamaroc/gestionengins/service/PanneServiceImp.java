package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Panne;
import com.marsamaroc.gestionengins.repository.PanneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PanneServiceImp implements PanneService {
    @Autowired
    PanneRepository panneRepository;
    @Override
    public Panne saveOrUpdatePagne(Panne panne) {
        return panneRepository.save(panne);
    }
}
