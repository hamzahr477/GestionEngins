package com.marsamaroc.gestionengins.entity;


import com.marsamaroc.gestionengins.enums.EtatEngin;
import com.marsamaroc.gestionengins.enums.DisponibiliteEnginParck;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
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
    private EtatEngin etat = EtatEngin.disponible;

    @Enumerated(EnumType.STRING)
    private DisponibiliteEnginParck disponibiliteParck = DisponibiliteEnginParck.indisponible;

    @OneToMany(mappedBy = "engin")
    private List<EnginAffecte> enginAffecteList;


    public Famille getFamille(){
        return famille;
    }

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

    public EnginAffecte getDerniereAffectation(){
        EnginAffecte dernierEnginAffecte = null;
        if(enginAffecteList!=null){
            dernierEnginAffecte = enginAffecteList.isEmpty() ? null : enginAffecteList.get(0);
            for(EnginAffecte enginAffecteitm : enginAffecteList){
                if(enginAffecteitm.getDateAffectation().compareTo(dernierEnginAffecte.getDateAffectation())>=1)
                    dernierEnginAffecte = enginAffecteitm;
            }
            return null;
        }
        return dernierEnginAffecte;
    }
}
