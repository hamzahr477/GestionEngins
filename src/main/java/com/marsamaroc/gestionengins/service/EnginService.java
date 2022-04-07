package com.marsamaroc.gestionengins.service;



import com.marsamaroc.gestionengins.dto.EnginDTO;
import com.marsamaroc.gestionengins.entity.Engin;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface EnginService {
    Engin getById(String id);

    List<Engin> getAll();
    List<Engin> getEnginsSorties();
    List<Engin> getEnginsEntrees();
    List<Engin> getEnginsEntreesByFamille(Long Famille);

    void save(Engin engin);

    Optional<Engin> findById(String id);
    EnginDTO saveOrUpdate(Engin engin);

    Engin update(Engin engin);
}
