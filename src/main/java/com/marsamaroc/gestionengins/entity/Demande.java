package com.marsamaroc.gestionengins.entity;

import com.marsamaroc.gestionengins.enums.EtatAffectation;
import com.marsamaroc.gestionengins.enums.EtatEngin;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Demande implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long numBCI;
    Date dateDemande;
    Date dateSortie;
    int shift;
    @ManyToOne
    @JoinColumn(name = "id_post")
    Post post;
    @OneToMany(mappedBy = "demande")
    List<DetailsDemande> detailsDemandeList;
    @OneToMany(mappedBy = "demande")
    List<EnginAffecte> enginsAffecteList;

    @ManyToOne
    @JoinColumn(name = "demande_responsable")
    private User user;

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
}
