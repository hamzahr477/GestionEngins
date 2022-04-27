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
    private UserDTO conducteur_entree;
    private UserDTO responsable_entree;
    private UserDTO conducteur_sortie;
    private UserDTO responsable_sortie;
    private Shift shift_sortie;
    private Shift shift_entree;
    private Long compteur_sortie;
    private Long compteur_entree;
    private Date dateEntree;
    private Date dateSortie;
    private Date observation_entree;
    private Date observation_sortie;
    //
    private List<CritereDemandeDTO> critere = new ArrayList<>();
    public EnginDTO(Engin engin, EnginAffecte enginAffecte){
        this.dateSortie = enginAffecte!=null? enginAffecte.getDateSortie() : this.dateSortie;
        this.dateEntree= enginAffecte!=null? enginAffecte.getDateEntree() : this.dateEntree;
        this.nomEngin = engin.getNomEngin();
        this.codeEngin = engin.getCodeEngin();
        this.typeEngin = engin.getTypeEngin();
        this.capacite = engin.getCapacite();
        this.compteur = engin.getCompteur();
        this.idDemandeEngin = enginAffecte!= null ? enginAffecte.getIdDemandeEngin() : null;
        Date dateDerniereAffectation=  enginAffecte!=null ? enginAffecte.getDateAffectation() : null ;
        this.famille = new FamilleDTO(engin.getFamille(),dateDerniereAffectation);
        if(enginAffecte != null){
            this.conducteur_entree =enginAffecte.getConducteur_entree() == null ? null :  new UserDTO(enginAffecte.getConducteur_entree());
            this.conducteur_sortie =enginAffecte.getConducteur_sortie() == null ? null :  new UserDTO(enginAffecte.getConducteur_sortie());
            this.responsable_sortie =enginAffecte.getResponsableAffectation_sortie() == null ? null :  new UserDTO(enginAffecte.getResponsableAffectation_sortie());
            this.responsable_entree =enginAffecte.getResponsableAffectation_entree() == null ? null :  new UserDTO(enginAffecte.getResponsableAffectation_entree());
            this.compteur_entree = enginAffecte.getCompteur_entree() != null ?enginAffecte.getCompteur_entree() : null;
            this.compteur_sortie = enginAffecte.getCompteur_sortie() != null ?enginAffecte.getCompteur_sortie() : null;
            this.shift_sortie = enginAffecte.getShift_sortie() ;
            this.shift_entree= enginAffecte.getShift_entree();
            if(enginAffecte.getControleEngin() == null) enginAffecte.setControleEngin(new ArrayList<>()) ;
            for (Controle controle : enginAffecte.getControleEngin())
                this.critere.add(new CritereDemandeDTO(controle));
        }

    }
}
