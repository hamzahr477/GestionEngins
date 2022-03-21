package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.DetailsPanne;
import com.marsamaroc.gestionengins.entity.Panne;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PagneDTO {
    private Long id;
    private EnginDTO engin;
    private EnginAffecteeDTO derniereAffectation;
    private DemandeDTO currentDemande;
    private List<DetailsPagneDTO> detailsPagneList = new ArrayList<>();

    public PagneDTO(Panne panne){
        this.id = panne.getId();
        this.derniereAffectation = new EnginAffecteeDTO(panne.getDernierAffectation()) ;
        this.currentDemande = new DemandeDTO(panne.getCurrentDemande());
        this.engin = new EnginDTO(panne.getEngin(), panne.getEngin().getDerniereAffectation());
        if(panne.getDetailsPanneList() != null)
            for(DetailsPanne detailsPanne : panne.getDetailsPanneList())
                detailsPagneList.add(new DetailsPagneDTO(detailsPanne));
    }
}
