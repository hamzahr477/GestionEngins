package com.marsamaroc.gestionengins.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Entite implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String entite;
    @OneToMany(mappedBy="entite")
    List<Post> posts;
    @OneToMany(mappedBy="entite")
    List<Utilisateur> utilisateurList;
    @OneToMany(mappedBy="entite")
    List<Demande> demandeList;
    //Parametrage
    private Boolean active = true;
    private Date dateCreation;
    private Date derniereModification;
    ////
}
