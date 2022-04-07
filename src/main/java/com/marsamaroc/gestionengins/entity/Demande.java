package com.marsamaroc.gestionengins.entity;

import com.marsamaroc.gestionengins.enums.EtatAffectation;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class  Demande implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long numBCI;
    Date dateDemande  = new Date();
    Date dateSortie;
    int shift;
    @ManyToOne
    @JoinColumn(name = "id_post")
    Post post;
    @ManyToOne
    @JoinColumn(name = "id_entite")
    Entite entite;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "demande_numbci")
    List<DetailsDemande> detailsDemandeList;
    @OneToMany(mappedBy = "demande")
    List<EnginAffecte> enginsAffecteList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "demande_responsable")
    private Utilisateur utilisateur;

    public int getQuantite(){
        int totalEngin=0;
        for(DetailsDemande detailsDemande : this.getDetailsDemandeList())
            totalEngin+= detailsDemande.getQuantite();
        return totalEngin;
    }
    public int getNumberEnginAfectee(){
    	
    	if(enginsAffecteList!= null)
    	{
    		return enginsAffecteList.size();
    	}
    	return 0;

    }
    public String getStatut(){
        if(this.enginsAffecteList == null)
            return "Enregistée";
        if(this.enginsAffecteList.isEmpty())
            return "Enregistée";
        return "Verifiée";
    }
    public EnginAffecte getEnginAffecte(String idEngin){
        for (EnginAffecte enginAffecte : enginsAffecteList){
            if(enginAffecte.getEngin().getCodeEngin().equals(idEngin))
                return enginAffecte;
        }

        return null;

    }
    public int getNumberEnginEntree(){
        int nombre = 0;
        if(enginsAffecteList!=null)
            for(EnginAffecte enginAffecte : enginsAffecteList)
                if(enginAffecte.getEtat()== EtatAffectation.execute)
                    nombre++;
        return nombre;
    }
    public int getNumberEnginSortie(){
        int nombre = 0;
        if(enginsAffecteList!=null)
            for(EnginAffecte enginAffecte : enginsAffecteList)
                if(enginAffecte.getEtat() == EtatAffectation.enexecution)
                    nombre++;
        return nombre;
    }
    public int getNumberEnginReserve(){
        int nombre = 0;
        if(enginsAffecteList!=null)
            for(EnginAffecte enginAffecte : enginsAffecteList)
                if(enginAffecte.getEtat() == EtatAffectation.reserve)
                    nombre++;
        return nombre;
    }
    public int getQuantiteByFamille(Long idFamille){
        if(detailsDemandeList != null)
            for(DetailsDemande detailsDemande : detailsDemandeList)
                if(detailsDemande.getFamille().getIdFamille() == idFamille)
                    return detailsDemande.getQuantite();
        return 0;
    }
    public int getNbrAffectByFamille(Long idFamille){
        int q=0;
        if(enginsAffecteList != null)
            for(EnginAffecte enginAffecte : enginsAffecteList)
                if(enginAffecte.getEngin().getFamille().getIdFamille() == idFamille)
                    q++;
        return q;
    }
}
