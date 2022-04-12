package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Demande;
import com.marsamaroc.gestionengins.entity.Engin;
import com.marsamaroc.gestionengins.entity.EnginAffecte;
import com.marsamaroc.gestionengins.enums.EtatAffectation;
import com.marsamaroc.gestionengins.repository.ControleRepository;
import com.marsamaroc.gestionengins.repository.EnginAffecteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnginAffecteServiceImp implements EnginAffecteService {
    @Autowired
    EnginAffecteRepository enginAffecteRepository;
    @Autowired
    ControleRepository contoleRepository;
    @Override
    public EnginAffecte saveEnginDemande(EnginAffecte enginAffecte) {
        EnginAffecte oldEnginAffect =  getByEnginAndDemande(enginAffecte.getEngin(),enginAffecte.getDemande());
        enginAffecte.setIdDemandeEngin(oldEnginAffect==null ? null : oldEnginAffect.getIdDemandeEngin());
        enginAffecte.setDateModified(new Date());
        return enginAffecteRepository.save(enginAffecte);
    }

    @Override
    public List<EnginAffecte> getAllAffectationByIdEngin(String id) {
        return enginAffecteRepository.findAllByEngin_CodeEngin(id);
    }

    @Override
    public EnginAffecte getById(Long id) {
        return enginAffecteRepository.getById(id);
    }

    @Override
    public Optional<EnginAffecte> findById(Long id) {
        return enginAffecteRepository.findById(id);
    }

    @Override
    public EnginAffecte getByEnginAndDemande(Engin engin, Demande demande) {
        return enginAffecteRepository.findByEnginCodeEnginAndDemandeNumBCI(engin.getCodeEngin(),demande.getNumBCI());
    }

    @Override
    public void delete(EnginAffecte enginAffecte) {
        contoleRepository.deleteByEnginAffecteIdDemandeEngin(enginAffecte.getIdDemandeEngin());
        enginAffecteRepository.delete(enginAffecte);
    }

    @Override
    public Page<EnginAffecte> getAll(String sortField, int offset, int pageSize, String sortType, String search, EtatAffectation type) {
        System.out.println(type);
        return enginAffecteRepository.search(type,search, PageRequest.of(offset,pageSize, Sort.by(Sort.Direction.fromString(sortType),sortField)));
    }
}
