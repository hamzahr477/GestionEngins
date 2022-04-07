package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.dto.EnginDTO;
import com.marsamaroc.gestionengins.entity.Engin;
import com.marsamaroc.gestionengins.repository.EnginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnginServiceImp implements EnginService{

    @Autowired
    EnginRepository enginRepository;
    @Override
    public Engin getById(String id) {
        return enginRepository.getById(id);
    }

    @Override
    public List<Engin> getEnginsSorties() {
        return enginRepository.findAllEnginAffecteAndPreSortie();
    }

    @Override
    public List<Engin> getEnginsEntrees() {
        return enginRepository.findAllEnginSortie();
    }

    @Override
    public void save(Engin engin) {
        enginRepository.save(engin);
    }

    @Override
    public Optional<Engin> findById(String id) {
        return enginRepository.findById(id);
    }

    @Override
    public EnginDTO saveOrUpdate(Engin engin) {
        Engin enginold = enginRepository.findByCodeEngin(engin.getCodeEngin());
        if(enginold!=null) enginold.sync(engin);
        else enginold = engin;
        return new EnginDTO(enginRepository.save(enginold),null);
    }


    @Override
    public Engin update(Engin engin) {
        Engin oldEngin = enginRepository.getById(engin.getCodeEngin());
        oldEngin.setEtat(engin.getEtat());
        return enginRepository.save(oldEngin);
    }

    @Override
	public List<Engin> getAll() {
		return enginRepository.findAll();
	}

	@Override
	public List<Engin> getEnginsEntreesByFamille(Long famille) {
		return enginRepository.findAllEnginEntreeByFamille(famille);
	}
}
