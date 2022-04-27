package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.Controle;
import com.marsamaroc.gestionengins.entity.Engin;
import com.marsamaroc.gestionengins.entity.EnginAffecte;
import com.marsamaroc.gestionengins.entity.Shift;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class EnginDispDTO {
    private String codeEngin;
    private String typeEngin;
    private String capacite;
    private Long compteur;
    private FamilleDTO famille;
    private String nomEngin;
    //
    private List<CritereDemandeDTO> critere = new ArrayList<>();
    public EnginDispDTO(Engin engin){
        this.nomEngin = engin.getNomEngin();
        this.codeEngin = engin.getCodeEngin();
        this.typeEngin = engin.getTypeEngin();
        this.capacite = engin.getCapacite();
        this.compteur = engin.getCompteur();
        this.famille = new FamilleDTO(engin.getFamille(),engin.getDerniereAffectation()!=null?engin.getDerniereAffectation().getDateAffectation():null);

    }
}
