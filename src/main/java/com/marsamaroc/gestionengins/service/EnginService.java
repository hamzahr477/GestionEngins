package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.dto.EnginDTO;
import com.marsamaroc.gestionengins.entity.Engin;
import com.marsamaroc.gestionengins.repository.EnginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnginService {

    @Autowired
    EnginRepository enginRepository;
    public Engin getById(String id) {
        return enginRepository.getById(id);
    }

    public List<Engin> getEnginsSorties() {
        return enginRepository.findAllEnginAffecteAndPreSortie();
    }

    public List<Engin> getEnginsEntrees() {
        return enginRepository.findAllEnginSortie();
    }

    public void save(Engin engin) {
        enginRepository.save(engin);
    }

    public Optional<Engin> findById(String id) {
        return enginRepository.findById(id);
    }

    public EnginDTO saveOrUpdate(Engin engin) {
        Engin enginold = enginRepository.findByCodeEngin(engin.getCodeEngin());
        if(enginold!=null) enginold.sync(engin);
        else enginold = engin;
        return new EnginDTO(enginRepository.save(enginold),null);
    }


    public Engin update(Engin engin) {
        Engin oldEngin = enginRepository.getById(engin.getCodeEngin());
        oldEngin.setEtat(engin.getEtat());
        return enginRepository.save(oldEngin);
    }

	public List<Engin> getAll() {
		return enginRepository.findAll();
	}

	public List<Engin> getEnginsEntreesByFamille(Long famille) {
		return enginRepository.findAllEnginEntreeByFamille(famille);
	}
}
