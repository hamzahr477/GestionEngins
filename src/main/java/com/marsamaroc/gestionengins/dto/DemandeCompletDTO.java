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
    private int shift;
    private Long poste;
    private String entite;
    private String statut;
    private List<FamilleDemandeDTO> familleDemandee = new ArrayList<>();
    private List<EnginDTO> engins;

    public DemandeCompletDTO(Demande demande,  List<EnginDTO> enginDTOList){
        this.numBCI = demande.getNumBCI();
        this.dateDemande = demande.getDateDemande();
        this.dateSortie = demande.getDateSortie();
        this.shift = demande.getShift();
        this.poste = demande.getPost().getCodePost();
        this.entite = demande.getEntite().getEntite();
        this.statut = demande.getStatut();
        for(DetailsDemande detailsDemande : demande.getDetailsDemandeList())
            this.familleDemandee.add(new FamilleDemandeDTO(detailsDemande));
        this.engins = enginDTOList;
    }
    private Demande convertToEntity(){
        Entite entite = new Entite();
        entite.setEntite(this.entite);
        Post post =  new Post();
        post.setCodePost(poste);
        Demande demande = new Demande();
        demande.setDateDemande(dateDemande);
        demande.setNumBCI(numBCI);
        demande.setPost(post);
        demande.setShift(shift);
        demande.setDateSortie(dateSortie);
        List<DetailsDemande> detailsDemandeList = new ArrayList<>();
        for(FamilleDemandeDTO familleDemandeDTO : familleDemandee)
            detailsDemandeList.add(familleDemandeDTO.convertToEntity(demande));
        demande.setDetailsDemandeList(detailsDemandeList);
        return demande;
    }
}
