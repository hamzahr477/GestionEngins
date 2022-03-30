package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Utilisateur;
import com.marsamaroc.gestionengins.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImp implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public Utilisateur saveUser(Utilisateur utilisateur) {
        return userRepository.save(utilisateur);
    }

    @Override
    public Utilisateur getUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public List<Utilisateur> getUsres() {
        return userRepository.findAll();
    }

    @Override
    public Utilisateur saveUserIfNotExist(Utilisateur utilisateur){
        System.out.println(utilisateur.getMatricule());
        Utilisateur utilisateur_Test = getUserByMatricule(utilisateur.getMatricule());
        if (utilisateur_Test == null)
            utilisateur_Test = saveUser(utilisateur);
        return utilisateur_Test;
    }
    @Override
    public Utilisateur getUserByMatricule(String matricule) {
        return userRepository.findByMatricule(matricule);
    }
}
