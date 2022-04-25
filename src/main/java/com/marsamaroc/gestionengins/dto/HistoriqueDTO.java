package com.marsamaroc.gestionengins.dto;



import com.marsamaroc.gestionengins.entity.Demande;

import com.marsamaroc.gestionengins.entity.EnginAffecte;

import com.marsamaroc.gestionengins.entity.Panne;

import com.marsamaroc.gestionengins.entity.Shift;

import com.marsamaroc.gestionengins.enums.EtatEngin;

import com.marsamaroc.gestionengins.service.ShiftService;

import lombok.Data;



import java.time.LocalDate;

import java.time.LocalDateTime;

import java.util.Date;

@Data

public class HistoriqueDTO{
    private Long numBCI;
    private Shift shift;
    private String poste;
    private EntiteDTO entite;
    private String codeEngin;
    private String nomEngin;
    private String type;
    private Date dateSortie;
    private Date dateEntree;
    private Date derniereAffectation;
    private String nomFamille;
    private UserDTO conducteur_entree;
    private UserDTO responsable_entree;
    private UserDTO conducteur_sortie;
    private UserDTO responsable_sortie;
    private Long compteur_entree;
    private Long compteur_sortie;
    private String statut;
    private PanneDTO panne;
    private Shift shift_entree;
    private Shift shift_sortie;
    private String observation_entree ;
    private String observation_sortie ;
    public HistoriqueDTO(EnginAffecte enginAffecte) {

        this.numBCI = enginAffecte.getDemande().getNumBCI();

        this.shift = enginAffecte.getDemande().getShift();
        this.shift_entree = enginAffecte.getShift_entree();
        this.shift_sortie = enginAffecte.getShift_sortie();
        this.observation_entree = enginAffecte.getObservation_entree();
        this.observation_sortie = enginAffecte.getObservation_sortie();
        this.poste = enginAffecte.getDemande().getPost().getCodePost();
        this.entite = enginAffecte.getDemande().getEntite() == null ? null : new EntiteDTO(enginAffecte.getDemande().getEntite());
        this.type = enginAffecte.getDateEntree()!= null? "Entrée" : "Sortie";
        this.codeEngin = enginAffecte.getEngin().getCodeEngin();
        this.nomEngin = enginAffecte.getEngin().getNomEngin();
        this.nomFamille = enginAffecte.getEngin().getFamille().getNomFamille();
        this.dateEntree  = enginAffecte.getDateEntree();
        this.dateSortie = enginAffecte.getDateSortie();
        this.derniereAffectation = enginAffecte.getEngin().getDerniereAffectation().getDateAffectation();
        this.conducteur_sortie =enginAffecte.getConducteur_sortie()!=null? new UserDTO(enginAffecte.getConducteur_sortie()) :null;
        this.conducteur_entree =enginAffecte.getConducteur_entree()!=null? new UserDTO(enginAffecte.getConducteur_entree()) :null;
        this.responsable_sortie =enginAffecte.getResponsableAffectation_sortie()!=null? new UserDTO(enginAffecte.getResponsableAffectation_sortie()) :null;
        this.responsable_entree =enginAffecte.getResponsableAffectation_entree()!=null? new UserDTO(enginAffecte.getResponsableAffectation_entree()) :null;
        this.compteur_entree =enginAffecte.getCompteur_entree() != null ? enginAffecte.getCompteur_entree() : null ;
        this.compteur_sortie = enginAffecte.getCompteur_sortie()!= null ? enginAffecte.getCompteur_sortie() : null ;
        this.statut="OK";

        if(enginAffecte.getEngin().getPanneList() != null)
            for(Panne panne :enginAffecte.getEngin().getPanneList() )
                if(panne.getCurrentDemande().getNumBCI() == enginAffecte.getDemande().getNumBCI()) {
                    this.statut="enpanne";
                    this.panne = new PanneDTO(panne);
                }

    }

}

