package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.Controle;
import lombok.Data;

@Data
public class CritereDemandeDTO {


    Long idCritere;
    String critere;
    char etatEntree;
    String observationEntree;
    char etatSortie;
    String observationSortie;
    
    public CritereDemandeDTO(Controle controle){
        this.idCritere = controle.getCritere().getIdCritere();
        this.critere = controle.getCritere().getNomCritere();
        this.etatEntree = controle.getEtatEntree();
        this.etatSortie = controle.getEtatSortie();
        this.observationEntree = controle.getObsEntree();
        this.observationSortie = controle.getObsSortie();
    }
}
