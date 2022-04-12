package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DemandeCompletDTO {
    private Long numBCI;
    private Date dateDemande;
    private Date dateSortie;
    private Shift shift;
    private Long poste;
    private EntiteDTO entite;
    private String statut;
    private List<FamilleDemandeDTO> familleDemandee = new ArrayList<>();
    private List<EnginDTO> engins;

    public DemandeCompletDTO(Demande demande,  List<EnginDTO> enginDTOList){
        this.numBCI = demande.getNumBCI();
        this.dateDemande = demande.getDateDemande();
        this.dateSortie = demande.getDateSortie();
        this.shift = demande.getShift();
        this.poste = demande.getPost().getCodePost();
        this.entite = demande.getEntite() == null ? null : new EntiteDTO(demande.getEntite());
        this.statut = demande.getStatut();
        for(DetailsDemande detailsDemande : demande.getDetailsDemandeList())
            this.familleDemandee.add(new FamilleDemandeDTO(detailsDemande));
        this.engins = enginDTOList;
    }

}
