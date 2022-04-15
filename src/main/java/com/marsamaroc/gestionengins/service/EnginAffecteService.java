package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Demande;
import com.marsamaroc.gestionengins.entity.Engin;
import com.marsamaroc.gestionengins.entity.EnginAffecte;
import com.marsamaroc.gestionengins.enums.EtatAffectation;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EnginAffecteService {

    EnginAffecte saveEnginDemande(EnginAffecte enginAffecte);
    List<EnginAffecte> getAllAffectationByIdEngin(String id);
    EnginAffecte getById(Long id);
    Optional<EnginAffecte> findById(Long id);
    EnginAffecte getByEnginAndDemande(Engin engin, Demande demande);
    void delete(EnginAffecte enginAffecte);


Page<EnginAffecte> getAll(String sortField, int offset, int pageSize, String familles, String conducteurs, String responsables, Date dateMaxAffectation, Date dateMinAffectation, Date dateMaxEntree, Date dateMinEntree, Date dateMaxSortie, String sortType, Long numBCI, String codeEngins, Date dateMinSortie, EtatAffectation type);
}
