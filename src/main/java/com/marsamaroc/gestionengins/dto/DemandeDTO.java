package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.Demande;
import com.marsamaroc.gestionengins.entity.Entite;
import com.marsamaroc.gestionengins.entity.Post;
import com.marsamaroc.gestionengins.entity.Shift;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DemandeDTO {

    private Long numBCI;
    private Date dateDemande;
    private Date dateSortie;
    private Shift shift;
    private Long poste;
    private EntiteDTO entite;
    private int totalEngins;
    private String statut;

    public DemandeDTO(Demande demande) {
        this.numBCI = demande.getNumBCI();
        this.dateDemande = demande.getDateDemande();
        this.dateSortie = demande.getDateSortie();
        this.shift = demande.getShift();
        this.poste = demande.getPost().getCodePost();
        this.entite = demande.getEntite() == null ? null : new EntiteDTO(demande.getEntite());
        this.totalEngins = demande.getQuantite();
        this.statut = demande.getStatut();
    }

}
