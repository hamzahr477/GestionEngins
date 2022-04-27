package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Societe;
import com.marsamaroc.gestionengins.entity.Utilisateur;
import com.marsamaroc.gestionengins.enums.TypeUser;
import com.marsamaroc.gestionengins.exception.ResourceNotFoundException;
import com.marsamaroc.gestionengins.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public Utilisateur saveUser(Utilisateur utilisateur) {
        return userRepository.save(utilisateur);
    }

    public Utilisateur getUserById(Long id) {
        return userRepository.getById(id);
    }

    public List<Utilisateur> getUsres() {
        return userRepository.findAll();
    }

    public Utilisateur saveUserIfNotExist(Utilisateur utilisateur){
        Utilisateur utilisateur_Test = getUserByMatricule(utilisateur.getMatricule());
        if (utilisateur_Test == null)
            utilisateur_Test = saveUser(utilisateur);
        return utilisateur_Test;
    }
    public Utilisateur removeSociete(Long id) throws ResourceNotFoundException {
        Utilisateur userOld =userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Utilisateur not found for id :: "+id));
        userOld.setActive(false);
        return userRepository.save(userOld);
    }
    public Utilisateur getUserByMatricule(String matricule) {
        return userRepository.findByMatricule(matricule);
    }

    public List<Utilisateur> getCondicteursDispo() {
        return userRepository.findAllByType(TypeUser.conducteur).orElse(null);
    }

    public List<Utilisateur> getResponsable() {
        return userRepository.findAllByType(TypeUser.responsable).orElse(new ArrayList<>());
    }


}
