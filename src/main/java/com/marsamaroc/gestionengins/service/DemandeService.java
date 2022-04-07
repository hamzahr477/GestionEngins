package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Demande;

import java.util.List;
import java.util.Optional;

public interface DemandeService {
    List<Demande> findAllDemande();
    Demande findAllDemandeComplet(Long id);
    Demande saveDamande(Demande demande);
    Demande getById(Long id);

    Optional<Demande> findById(Long id);

    List<Demande> findAllDemandeEnregistree();

    void deletDemande(Demande demande);
    List<Demande> findAllDemandeVerifiee();
}
