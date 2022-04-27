package com.marsamaroc.gestionengins.entity;


import com.marsamaroc.gestionengins.enums.EtatAffectation;
import com.marsamaroc.gestionengins.enums.EtatEngin;
import com.marsamaroc.gestionengins.enums.DisponibiliteEnginParck;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;

@Data
@Entity
@ToString
public class Engin implements Serializable {
    @Id
    private String codeEngin;
    private String nomEngin;
    private String typeEngin;
    private String capacite;
    private Long compteur;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_famille")
    private Famille famille;
    @Enumerated(EnumType.STRING)
    private EtatEngin etat = EtatEngin.parcking;
    @Enumerated(EnumType.STRING)
    private DisponibiliteEnginParck disponibiliteParck = DisponibiliteEnginParck.indisponible;
    @OneToMany(mappedBy = "engin")
    private List<EnginAffecte> enginAffecteList;
    @OneToMany(mappedBy = "engin")
    List<Panne> panneList ;

    //Parametrage
    private Boolean active = true;
    private Date dateCreation;
    private Date derniereModification;
    ////
    public void sync(Engin engin){
        if(engin == null) return;
        this.codeEngin = engin.getCodeEngin()!= null ? engin.getCodeEngin() : this.codeEngin;
        this.nomEngin = engin.getNomEngin()!= null ? engin.getNomEngin() : this.nomEngin;
        this.typeEngin = engin.getTypeEngin()!= null ? engin.getTypeEngin() : this.typeEngin;
        this.capacite = engin.getCapacite()!= null ? engin.getCapacite() : this.capacite;
        this.compteur = engin.getCompteur()!= null ? engin.getCompteur() : this.compteur;
        this.famille = engin.getFamille() != null ? engin.getFamille() : this.famille;
        this.disponibiliteParck = engin.getDisponibiliteParck()!=null ? engin.getDisponibiliteParck() : this.disponibiliteParck;
    }

    public EnginAffecte

    getDerniereAffectation(){
        EnginAffecte dernierEnginAffecte = null;
        if(enginAffecteList!=null){
            Collections.sort(this.enginAffecteList, (o1, o2) -> o1.getDateAffectation().compareTo(o2.getDateAffectation()) < 0 ?  1 : -1);
            if(this.etat == EtatEngin.sortie && enginAffecteList.size()>1)
                dernierEnginAffecte = enginAffecteList.get(1);
            else     if(!enginAffecteList.isEmpty())
                dernierEnginAffecte = enginAffecteList.get(0);
        }
        return dernierEnginAffecte;
    }
    public EnginAffecte getCurrenteAffectation(){
        EnginAffecte dernierEnginAffecte = null;
        if(enginAffecteList!=null){
            Collections.sort(this.enginAffecteList, (o1, o2) -> o1.getDateAffectation().compareTo(o2.getDateAffectation()) < 0 ?  1 : -1);
            if(!enginAffecteList.isEmpty())
                dernierEnginAffecte = enginAffecteList.get(0);
        }
        return dernierEnginAffecte;
    }
    public Panne getDernierePanne(){
        Panne panne = null;
        if(this.panneList!=null){
            Collections.sort(this.panneList, (o1, o2) -> o1.getDateCreation().compareTo(o2.getDateCreation()) < 0 ?  1 : -1);
            if(!panneList.isEmpty())
                panne = panneList.get(0);
        }
        return panne;
    }
    public String getState(LocalTime maxShift){
        if(this.getCurrenteAffectation()!=null) {
            if (this.getCurrenteAffectation().getEtat() == EtatAffectation.enexecution)
                return "Non Retour";
            if(this.getCurrenteAffectation().getEtat() == EtatAffectation.reserve && this.getCurrenteAffectation().getDemande().isValableToTrait(maxShift))
                return "Reserve";
        }
        return "Disponible";
    }
}
