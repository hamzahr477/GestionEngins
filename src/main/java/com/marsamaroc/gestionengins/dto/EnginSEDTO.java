package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.Engin;
import com.marsamaroc.gestionengins.entity.Shift;
import com.marsamaroc.gestionengins.enums.EtatAffectation;
import com.marsamaroc.gestionengins.enums.EtatEngin;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class EnginSEDTO {

    private Long numBCI;
    private Shift shift;
    private String poste;
    private EntiteDTO entite;
    private String codeEngin;
    private String nomEngin;
    private Date dateSortie;
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
    private Date dateDernierAffectation;
    public EnginSEDTO(Engin engin){
        this.numBCI = engin.getCurrenteAffectation().getDemande().getNumBCI();
        this.shift = engin.getCurrenteAffectation().getDemande().getShift();
        this.poste = engin.getCurrenteAffectation().getDemande().getPost().getCodePost();
        this.entite = engin.getCurrenteAffectation().getDemande().getEntite() == null ? null : new EntiteDTO(engin.getCurrenteAffectation().getDemande().getEntite());
        this.conducteur_sortie =engin.getCurrenteAffectation().getConducteur_sortie()!=null? new UserDTO(engin.getCurrenteAffectation().getConducteur_sortie()) :null;
        this.conducteur_entree =engin.getCurrenteAffectation().getConducteur_entree()!=null? new UserDTO(engin.getCurrenteAffectation().getConducteur_entree()) :null;
        this.responsable_sortie =engin.getCurrenteAffectation().getResponsableAffectation_sortie()!=null? new UserDTO(engin.getCurrenteAffectation().getResponsableAffectation_sortie()) :null;
        this.responsable_entree =engin.getCurrenteAffectation().getResponsableAffectation_entree()!=null? new UserDTO(engin.getCurrenteAffectation().getResponsableAffectation_entree()) :null;
        this.nomEngin = engin.getNomEngin();
        this.codeEngin = engin.getCodeEngin();
        this.dateSortie = engin.getCurrenteAffectation().getDateSortie();
        this.compteur_entree =engin.getCurrenteAffectation().getCompteur_entree() != null ? engin.getCurrenteAffectation().getCompteur_entree() : null ;
        this.compteur_sortie =engin.getCurrenteAffectation().getCompteur_sortie()!= null ? engin.getCurrenteAffectation().getCompteur_sortie() : null ;
        this.nomFamille = engin.getFamille().getNomFamille();
        this.dateDernierAffectation = engin.getDerniereAffectation() != null ? engin.getDerniereAffectation().getDateAffectation() : null;
    }
}
