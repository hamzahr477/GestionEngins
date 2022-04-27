package com.marsamaroc.gestionengins.service;


import com.marsamaroc.gestionengins.entity.*;
import com.marsamaroc.gestionengins.exception.ResourceNotFoundException;
import com.marsamaroc.gestionengins.repository.DemandeRepository;
import com.marsamaroc.gestionengins.repository.EnginAffecteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class DemandeService   {

    @Autowired
    DemandeRepository demandeRepository;

    @Autowired
    EnginAffecteRepository enginAffecteRepository;

    public List<Demande> findAllDemande() {
        List<Demande> demandeList = demandeRepository.findAllByOrderByDateSortieDesc();
        return demandeList;
    }

    public Demande findAllDemandeComplet(Long id) {
        Demande demande = demandeRepository.findById(id).get();
        return demande;
    }
    public Page<Demande> getAvailbleDemandeEnregistrer(Shift maxShift ,int offset, int pageSize) throws ResourceNotFoundException {
        List<Demande> demandeList = demandeRepository.findDemandeEnregistre().orElseThrow(()->new ResourceNotFoundException("Demandes not found"));
        for(Demande demande : demandeList)
            if(demande.isValableToTrait(maxShift.getHeureFin()))
                demandeList.remove(demande);
        if(demandeList.isEmpty()) throw new ResourceNotFoundException("Demandes not found");
        Page<Demande> demandePage= new PageImpl<>(demandeList, PageRequest.of(pageSize,offset),demandeList.size());
        return demandePage;
    }
    public Page<Demande> getAvailbleDemandeVerifier(Shift maxShift ,int offset, int pageSize) throws ResourceNotFoundException {
        List<Demande> demandeList = demandeRepository.findDemandeVerifie().orElseThrow(()->new ResourceNotFoundException("Demandes not found"));
        for(Demande demande : demandeList)
            if(demande.isValableToTrait(maxShift.getHeureFin()))
                demandeList.remove(demande);
        if(demandeList.isEmpty()) throw new ResourceNotFoundException("Demandes not found");
        Page<Demande> demandePage= new PageImpl<>(demandeList, PageRequest.of(pageSize,offset),demandeList.size());
        return demandePage;
    }


    public Demande saveDamande(Demande demande) {
        return demandeRepository.save(demande);
    }

    public Demande getById(Long id) {
        return demandeRepository.getById(id);
    }

    public Optional<Demande> findById(Long id) {
        return demandeRepository.findById(id);
    }

    public List<Demande> findAllDemandeEnregistree() {
        return demandeRepository.findByEnginsAffecteListIsNull();
    }

    public void deletDemande(Demande demande) throws ResourceNotFoundException {
        Demande demandeOld = demandeRepository.findById(demande.getNumBCI()).orElseThrow(()->new ResourceNotFoundException("Demande not found for id :: "+demande.getNumBCI()));
        demandeOld.setActive(false);
        demandeRepository.save(demandeOld);
    }

    public List<Demande> findAllDemandeVerifiee() {
        return demandeRepository.findByEnginsAffecteListIsNotNull();
    }
}
