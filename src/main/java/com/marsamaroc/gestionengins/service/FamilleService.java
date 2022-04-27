package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Engin;
import com.marsamaroc.gestionengins.entity.Famille;
import com.marsamaroc.gestionengins.exception.ResourceNotFoundException;
import com.marsamaroc.gestionengins.repository.FamilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilleService  {

    @Autowired
    FamilleRepository familleRepository;

    public Famille getById(Long idFamille) {
        return familleRepository.getById(idFamille);
    }

    public Famille saveFamille(Famille famille) {
        return familleRepository.save(famille);
    }

    public Famille removeFamille(Long id) throws ResourceNotFoundException {
        Famille familleOld = familleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Famille not found for id :: "+id));
        familleOld.setActive(false);
        return familleRepository.save(familleOld);
    }
    public Famille getFamilleByName(String nomFamille) {
        return familleRepository.findByNomFamille(nomFamille);
    }
}
