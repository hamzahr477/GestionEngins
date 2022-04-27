package com.marsamaroc.gestionengins.entity;

import com.marsamaroc.gestionengins.enums.EtatEngin;
import com.marsamaroc.gestionengins.enums.TypeUser;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
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
    private char enable='N';
    @Enumerated(EnumType.STRING)
    private TypeUser type;


    @ManyToOne
    @JoinColumn(name = "id_entite")
    private Entite entite;

    @ManyToOne
    @JoinColumn(name = "id_societe")
    private Societe societe;

    @OneToMany(mappedBy= "utilisateur")
    private List<Demande> demandeList;

    //Parametrage
    private Boolean active = true;
    private Date dateCreation;
    private Date derniereModification;
    ////
    /*
    public boolean isDisponible(){
        if(enginAffecteList_conducteur!=null){
            Collections.sort(this.enginAffecteList_conducteur, (o1, o2) -> o1.getDateSortie().compareTo(o2.getDateSortie()) <= 0 ?  1 : -1);
            if(!enginAffecteList_conducteur.isEmpty())
                return DateUtils.addHours(enginAffecteList_conducteur.get(0).getDateSortie(), enginAffecteList_conducteur.get(0).getDemande().getShift().getHeureFin().getHour()).compareTo(new Date()) <= 0 || enginAffecteList_conducteur.get(0).getDateEntree()!=null;
        }
        return true;
    }
     */



}

