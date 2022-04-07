package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.entity.Utilisateur;

import java.util.List;

public interface UserService {
    Utilisateur saveUser(Utilisateur utilisateur);
    Utilisateur getUserById(Long id);
    List<Utilisateur> getUsres();

    Utilisateur saveUserIfNotExist(Utilisateur utilisateur);

    Utilisateur getUserByMatricule(String matricule);

    List<Utilisateur> getCondicteursDispo();

    List<Utilisateur> getResponsable();

}
