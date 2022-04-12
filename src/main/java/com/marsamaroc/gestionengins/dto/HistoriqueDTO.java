package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.Demande;
import com.marsamaroc.gestionengins.entity.EnginAffecte;
import com.marsamaroc.gestionengins.entity.Shift;
import com.marsamaroc.gestionengins.enums.EtatEngin;
import com.marsamaroc.gestionengins.service.ShiftServiceImp;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Data
public class HistoriqueDTO{
    private Long numBCI;
    private Shift shift;
    private Long poste;
    private EntiteDTO entite;
    private String codeEngin;
    private String nomEngin ;
    private String type;
    private Date dateSortieR;
    private Date dateEntreeR;
    private Date derniereAffectation;
    private String nomFamille;
    private UserDTO conducteur;
    private UserDTO responsable;
    private String statut;
    private PanneDTO panne;
    public HistoriqueDTO(EnginAffecte enginAffecte) {
        this.numBCI = enginAffecte.getDemande().getNumBCI();
        this.shift = enginAffecte.getDemande().getShift();
        this.poste = enginAffecte.getDemande().getPost().getCodePost();
        this.entite = enginAffecte.getDemande().getEntite() == null ? null : new EntiteDTO(enginAffecte.getDemande().getEntite());
        this.type = enginAffecte.getDateEntree()!= null? "Entrée" : "Sortie";
        this.codeEngin = enginAffecte.getEngin().getCodeEngin();
        this.nomEngin = enginAffecte.getEngin().getNomEngin();
        this.nomFamille = enginAffecte.getEngin().getFamille().getNomFamille();
        this.dateEntreeR  = enginAffecte.getDateEntree();
        this.dateSortieR = enginAffecte.getDateSortie();
        this.derniereAffectation = enginAffecte.getEngin().getDerniereAffectation().getDateAffectation();
        this.conducteur =enginAffecte.getConducteur()!=null? new UserDTO(enginAffecte.getConducteur()) :null;
        this.responsable =enginAffecte.getResponsableAffectation()!=null? new UserDTO(enginAffecte.getResponsableAffectation()) :null;
        this.statut = enginAffecte.getEngin().getEtat()== EtatEngin.enpanne ? "enpanne" : "ok";
        this.panne =enginAffecte.getEngin().getDernierePanne()!= null?  new PanneDTO(enginAffecte.getEngin().getDernierePanne()):null ;

    }
}
