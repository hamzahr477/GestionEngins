package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.Critere;
import com.marsamaroc.gestionengins.entity.Societe;
import lombok.Data;

@Data
public class SocieteDTO {

    private Long id;
    private String codeSociete;
    private String nomSociete;

    public SocieteDTO(Societe societe){
        this.id = societe.getId();
        this.codeSociete = societe.getCodeSociete();
        this.nomSociete= societe.getNomSociete();

    }
}
