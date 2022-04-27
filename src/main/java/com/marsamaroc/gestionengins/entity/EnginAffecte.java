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

    Date dateModified = new Date() ;
    @ManyToOne
    @JoinColumn(name = "affectation_responsable_entree")
    private Utilisateur responsableAffectation_entree;

    @ManyToOne
    @JoinColumn(name = "conducteur_entree")
    private Utilisateur conducteur_entree;
    @ManyToOne
    @JoinColumn(name = "affectation_responsable_sortie")
    private Utilisateur responsableAffectation_sortie;

    @ManyToOne
    @JoinColumn(name = "conducteur_sortie")
    private Utilisateur conducteur_sortie;

    Long compteur_entree;
    Long compteur_sortie;
    @ManyToOne
    @JoinColumn(name = "id_shift_entree")
    Shift shift_entree;
    @ManyToOne
    @JoinColumn(name = "id_shift_sortie")
    Shift shift_sortie;

    private String observation_entree;
    private String observation_sortie;

    //Parametrage
    private Boolean active = true;
    ////

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
        this.responsableAffectation_entree = enginAffecte.responsableAffectation_entree != null ? enginAffecte.getResponsableAffectation_entree() : this.responsableAffectation_entree;
        this.responsableAffectation_sortie = enginAffecte.responsableAffectation_sortie != null ? enginAffecte.getResponsableAffectation_sortie() : this.responsableAffectation_sortie;
        this.conducteur_entree = enginAffecte.conducteur_entree != null ? enginAffecte.getConducteur_entree() : this.conducteur_entree;
        this.conducteur_entree = enginAffecte.conducteur_sortie != null ? enginAffecte.getConducteur_sortie() : this.conducteur_sortie;
        this.compteur_entree = enginAffecte.compteur_entree != null ? enginAffecte.getCompteur_entree() : this.compteur_entree;
    }

}
