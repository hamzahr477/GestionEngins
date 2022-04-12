package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.DetailsPanne;
import com.marsamaroc.gestionengins.entity.Panne;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PanneDTO {
    private Long id;
    private EnginDTO engin;
    private EnginAffecteeDTO derniereAffectation;
    private DemandeDTO currentDemande;
    private List<DetailsPagneDTO> detailsPagneList = new ArrayList<>();

    public PanneDTO(Panne panne){
        this.id = panne.getId();
        this.derniereAffectation = panne != null? new EnginAffecteeDTO(panne.getDernierAffectation()) :null;
        this.currentDemande =panne != null?  new DemandeDTO(panne.getCurrentDemande()):null;
        if(panne.getDetailsPanneList() != null)
            for(DetailsPanne detailsPanne : panne.getDetailsPanneList())
                detailsPagneList.add(new DetailsPagneDTO(detailsPanne));
    }
}
