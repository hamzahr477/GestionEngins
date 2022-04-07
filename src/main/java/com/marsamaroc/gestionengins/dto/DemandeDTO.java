package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.Demande;
import com.marsamaroc.gestionengins.entity.Entite;
import com.marsamaroc.gestionengins.entity.Post;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DemandeDTO {

    private Long numBCI;
    private Date dateDemande;
    private Date dateSortie;
    private int shift;
    private Long poste;
    private String entite;
    private int totalEngins;
    private String statut;

    public DemandeDTO(Demande demande) {
        this.numBCI = demande.getNumBCI();
        this.dateDemande = demande.getDateDemande();
        this.dateSortie = demande.getDateSortie();
        this.shift = demande.getShift();
        this.poste = demande.getPost().getCodePost();
        this.entite = demande.getEntite() == null ? null : demande.getEntite().getEntite();
        this.totalEngins = demande.getQuantite();
        this.statut = demande.getStatut();
    }
    private Demande toEntity(){
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
        return demande;
    }
}
