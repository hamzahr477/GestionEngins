package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Societe;
import com.marsamaroc.gestionengins.exception.ResourceNotFoundException;
import com.marsamaroc.gestionengins.repository.SocieteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SocieteService {
    @Autowired
    SocieteRepository societeRepository;

    public Societe addSociete(Societe societe){
        societe.setDateCreation(new Date());
        return societeRepository.save(societe);
    }
    public Societe editSociete(Societe societe){
        Societe societeOld =societeRepository.getById(societe.getId());
        societeOld.setCodeSociete(societe.getCodeSociete());
        societeOld.setNomSociete(societe.getNomSociete());
        societeOld.setDerniereModification(new Date());
        return societeRepository.save(societeOld);
    }
    public List<Societe> getAllSocietes(Societe societe){
        return societeRepository.findAll();
    }
    public Societe removeSociete(Long id) throws ResourceNotFoundException {
        Societe societeOld =societeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Societe not found for id :: "+id));
        societeOld.setActive(false);
        return societeRepository.save(societeOld);
    }
}
