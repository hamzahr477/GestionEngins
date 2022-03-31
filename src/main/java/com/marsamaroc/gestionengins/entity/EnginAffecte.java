package com.marsamaroc.gestionengins.entity;

import com.marsamaroc.gestionengins.enums.EtatAffectation;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class EnginAffecte implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idDemandeEngin;

    @ManyToOne
    @JoinColumn(name = "id_demande")
    Demande demande;
    @OneToMany(mappedBy = "enginAffecte")
    List<Controle> controleEngin;
    @Enumerated(EnumType.STRING)
    EtatAffectation etat = EtatAffectation.reserve;
    @ManyToOne
    @JoinColumn(name = "id_engin")
    Engin engin;
    Date dateEntree;
    Date dateSortie;
    Date dateAffectation = new Date();

    @ManyToOne
    @JoinColumn(name = "affectation_responsable")
    private Utilisateur responsableAffectation;

    @ManyToOne
    @JoinColumn(name = "conducteur")
    private Utilisateur conducteur;



    public void sync(EnginAffecte enginAffecte){
        this.idDemandeEngin = enginAffecte.idDemandeEngin != null ? enginAffecte.getIdDemandeEngin() : this.idDemandeEngin;
        this.demande = enginAffecte.demande != null ? enginAffecte.getDemande() : this.demande;

        if(enginAffecte.controleEngin != null )
            for(int i= 0;i< enginAffecte.getControleEngin().size();i++)
                this.controleEngin.get(i).sync(enginAffecte.controleEngin.get(i));
        this.etat = enginAffecte.etat != null ? enginAffecte.getEtat() : this.etat;
        this.engin = enginAffecte.engin != null ? enginAffecte.getEngin() : this.engin;
        this.dateEntree = enginAffecte.dateEntree != null ? enginAffecte.getDateEntree() : this.dateEntree;
        this.dateSortie = enginAffecte.dateSortie != null ? enginAffecte.getDateSortie() : this.dateSortie;
        this.dateAffectation = enginAffecte.dateAffectation != null ? enginAffecte.getDateAffectation() : this.dateAffectation;

    }
}
