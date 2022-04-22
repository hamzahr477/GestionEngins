package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.Engin;
import com.marsamaroc.gestionengins.enums.EtatAffectation;
import com.marsamaroc.gestionengins.enums.EtatEngin;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class EnginSEDTO {

    private String codeEngin;
    private String nomEngin;
    private String typeEngin;
    private String capacite;
    private Long compteur;
    private String famille;
    private Date dateDernierAffectation;
    private EtatAffectation type;
    private Long numBCI;

    public EnginSEDTO(Engin engin){
        this.nomEngin = engin.getNomEngin();
        this.codeEngin = engin.getCodeEngin();
        this.typeEngin = engin.getTypeEngin() ;
        this.capacite = engin.getCapacite();
        this.compteur = engin.getCompteur();
        this.famille = engin.getFamille().getNomFamille();
        this.dateDernierAffectation = engin.getDerniereAffectation() != null ? engin.getDerniereAffectation().getDateAffectation() : null;
        this.type = engin.getDerniereAffectation() != null ? engin.getDerniereAffectation().getEtat()  : null;
        this.numBCI = engin.getDerniereAffectation() != null ? engin.getDerniereAffectation().getDemande().getNumBCI() : null;
    }
}
