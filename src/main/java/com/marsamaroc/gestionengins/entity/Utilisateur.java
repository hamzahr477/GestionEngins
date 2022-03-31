package com.marsamaroc.gestionengins.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@ToString
public class Utilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String matricule;
    private String email;
    private String password;
    private char enable;
    private String type;

    @ManyToOne
    @JoinColumn(name = "id_entite")
    private Entite entite;

    @OneToMany(mappedBy= "utilisateur")
    private List<Demande> demandeList;

    @OneToMany(mappedBy = "responsableAffectation")
    private List<EnginAffecte> enginAffecteList;

    @OneToMany(mappedBy = "conducteur")
    private List<EnginAffecte> enginAffecteList_conducteur;




}

