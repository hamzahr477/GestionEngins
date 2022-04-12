package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.Entite;
import lombok.Data;

@Data
public class EntiteDTO {
    private Long id;
    private String entite;
    public
    EntiteDTO(Entite entite){
        this.id = entite.getId();
        this.entite = entite.getEntite();
    }
}
