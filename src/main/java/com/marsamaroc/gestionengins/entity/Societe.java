package com.marsamaroc.gestionengins.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Societe implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String codeSociete;
    private String nomSociete;
    private Boolean active = true;
    private Date dateCreation;
    private Date derniereModification;

    @OneToMany(mappedBy = "societe")
    List<Utilisateur> utilisateurList;
}
