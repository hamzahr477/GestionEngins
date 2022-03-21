package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Famille;

public interface FamilleService {
    Famille getById(Long id);

    Famille saveFamille(Famille famille);
    Famille getFamilleByName(String nomFamille);
}
