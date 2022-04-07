package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.Utilisateur;

import com.marsamaroc.gestionengins.enums.TypeUser;
import lombok.Data;

@Data
public class UserDTO {
	
	     Long id;
	     String nom;
	     String prenom;
	     String matricule;
	     String email;
	     String password;
	     char enable;
	     TypeUser type;
	    
//-----
    
	public UserDTO(Utilisateur utilisateur){
        this.id = utilisateur.getId();
        this.prenom = utilisateur.getPrenom();
        this.matricule = utilisateur.getMatricule();
        this.email = utilisateur.getEmail();
        this.password = utilisateur.getPassword();
        this.enable = utilisateur.getEnable();
        this.type = utilisateur.getType();
        this.nom = utilisateur.getNom();

    }
    
}
