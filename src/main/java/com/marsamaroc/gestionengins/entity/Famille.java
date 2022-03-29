package com.marsamaroc.gestionengins.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Famille implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idFamille;
    String codeFamille;
    String nomFamille;
    String icon;
    boolean deleted = false;

    @OneToMany(mappedBy="famille")
    List<Engin> engin;

}
