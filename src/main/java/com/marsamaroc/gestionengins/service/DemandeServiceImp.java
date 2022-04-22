package com.marsamaroc.gestionengins.service;


import com.marsamaroc.gestionengins.entity.*;
import com.marsamaroc.gestionengins.repository.DemandeRepository;
import com.marsamaroc.gestionengins.repository.EnginAffecteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DemandeServiceImp implements DemandeService {

    @Autowired
    DemandeRepository demandeRepository;

    @Autowired
    EnginAffecteRepository enginAffecteRepository;

    @Override
    public List<Demande> findAllDemande() {
        List<Demande> demandeList = demandeRepository.findAllByOrderByDateSortieDesc();
        return demandeList;
    }

    @Override
    public Demande findAllDemandeComplet(Long id) {
        Demande demande = demandeRepository.findById(id).get();
        return demande;
    }


    @Override
    public Demande saveDamande(Demande demande) {
        return demandeRepository.save(demande);
    }

    @Override
    public Demande getById(Long id) {
        return demandeRepository.getById(id);
    }

    @Override
    public Optional<Demande> findById(Long id) {
        return demandeRepository.findById(id);
    }

    @Override
    public List<Demande> findAllDemandeEnregistree() {
        return demandeRepository.findByEnginsAffecteListIsNull();
    }

    @Override
    public void deletDemande(Demande demande) {
        demandeRepository.delete(demande);
    }

    @Override
    public List<Demande> findAllDemandeVerifiee() {
        return demandeRepository.findByEnginsAffecteListIsNotNull();
    }
}
