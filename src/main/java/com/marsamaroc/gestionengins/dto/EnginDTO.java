package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class EnginDTO {
    private String codeEngin;
    private String typeEngin;
    private String capacite;
    private Long compteur;
    private FamilleDTO famille;
    private Long idDemandeEngin;
    private String nomEngin;
    private UserDTO conducteur;
    private UserDTO responsable;
    //
    private List<CritereDemandeDTO> critere = new ArrayList<>();
    public EnginDTO(Engin engin, EnginAffecte enginAffecte){
        this.nomEngin = engin.getNomEngin();
        this.codeEngin = engin.getCodeEngin();
        this.typeEngin = engin.getTypeEngin();
        this.capacite = engin.getCapacite();
        this.compteur = engin.getCompteur();
        this.idDemandeEngin = enginAffecte!= null ? enginAffecte.getIdDemandeEngin() : null;
        Date dateDerniereAffectation=  enginAffecte!=null ? enginAffecte.getDateAffectation() : null ;
        this.famille = new FamilleDTO(engin.getFamille(),dateDerniereAffectation);
        if(enginAffecte != null){
            this.conducteur =enginAffecte.getConducteur() == null ? null :  new UserDTO(enginAffecte.getConducteur());
            this.responsable =enginAffecte.getResponsableAffectation() == null ? null :  new UserDTO(enginAffecte.getResponsableAffectation());
            if(enginAffecte.getControleEngin() == null) enginAffecte.setControleEngin(new ArrayList<>()) ;
            for (Controle controle : enginAffecte.getControleEngin())
                this.critere.add(new CritereDemandeDTO(controle));
        }

    }
}
