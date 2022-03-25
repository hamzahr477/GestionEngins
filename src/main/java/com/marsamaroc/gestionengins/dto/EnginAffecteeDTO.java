package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.EnginAffecte;
import lombok.Data;

import java.util.Date;

@Data
public class EnginAffecteeDTO {
    private Long idEnginAffect;
    private String codeEngin ;
    private Long numBCI;
    private Date dateEntre;
    private Date dateSortie;

    public EnginAffecteeDTO(EnginAffecte enginAffecte){
        dateSortie = enginAffecte.getDateSortie();
        dateEntre = enginAffecte.getDateEntree();
        idEnginAffect = enginAffecte != null ?enginAffecte.getIdDemandeEngin(): null ;
        codeEngin = enginAffecte != null ? enginAffecte.getEngin().getCodeEngin(): null ;
        numBCI = enginAffecte != null ? enginAffecte.getDemande().getNumBCI(): null ;
    }

}
