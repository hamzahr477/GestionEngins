package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Controle;
import com.marsamaroc.gestionengins.entity.EnginAffecte;
import com.marsamaroc.gestionengins.repository.ControleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ControleService  {

    @Autowired
    ControleRepository controleRepository;
    public Controle getById(Long id) {
        return controleRepository.getById(id);
    }

    public void save(Controle controle, EnginAffecte enginAffecte) {
        controle.setEnginAffecte(enginAffecte);
        controleRepository.save(controle);
    }

    public Controle getControlByIdCritereAndIdAffectation(Long idCritere, Long idAffectation) {

        return controleRepository.findAllByCritere_IdCritereAndEnginAffecte_IdDemandeEngin(idCritere,idAffectation);
    }

    public void saveAll(List<Controle> enginControleList) {
        controleRepository.saveAll(enginControleList);
    }
}
