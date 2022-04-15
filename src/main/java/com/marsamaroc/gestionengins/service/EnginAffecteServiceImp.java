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

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Page<EnginAffecte> getAll(String sortField,
                                     int offset,
                                     int pageSize,
                                     String familles,
                                     String conducteurs,
                                     String responsables,
                                     Date dateMaxAffectation,
                                     Date dateMinAffectation,
                                     Date dateMaxEntree,
                                     Date dateMinEntree,
                                     Date dateMaxSortie,
                                     String sortType,
                                     Long numBCI,
                                     String codeEngins,
                                     Date dateMinSortie,
                                     EtatAffectation type){
        List<Long> familles_ = familles == null? null : Arrays.stream(familles.split(",")).map(f->Long.parseLong(f)).collect(Collectors.toList());
        List<String> conducteurs_ = conducteurs == null? null: Arrays.asList(conducteurs.split(","));
        List<String> responsables_ = responsables == null? null: Arrays.asList(responsables.split(","));
        List<String> codeEngins_ = codeEngins == null? null: Arrays.asList(codeEngins.split(","));
        System.out.println(type+":"+":"+familles+":"+codeEngins+":"+numBCI+":"+conducteurs+":"+responsables+":"+dateMinSortie+":"+dateMaxAffectation+":"+dateMinAffectation+":"+dateMaxEntree+":"+dateMinEntree+":"+dateMaxSortie);
        return enginAffecteRepository.search(type,familles_,codeEngins_,numBCI,conducteurs_,responsables_,dateMinSortie,dateMaxAffectation,dateMinAffectation,dateMaxEntree,dateMinEntree,dateMaxSortie, PageRequest.of(offset,pageSize, Sort.by(Sort.Direction.fromString(sortType),sortField)));
    }
}
