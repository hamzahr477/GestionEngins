package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Demande;
import com.marsamaroc.gestionengins.entity.DetailsDemande;
import com.marsamaroc.gestionengins.repository.DetailsDemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailsDemandeService   {

    @Autowired
    DetailsDemandeRepository detailsDemandeRepository;



    public DetailsDemande getDetailsDemandeByNumBciAndIdFammille(Long numBci, Long idFamille) {
        return detailsDemandeRepository.findByDemande_NumBCIAndFamille_IdFamille(numBci,idFamille);
    }
}
