package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Panne;
import com.marsamaroc.gestionengins.repository.PanneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PanneService  {
    @Autowired
    PanneRepository panneRepository;
    public Panne saveOrUpdatePagne(Panne panne) {
        return panneRepository.save(panne);
    }
}
