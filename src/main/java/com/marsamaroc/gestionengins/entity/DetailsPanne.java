package com.marsamaroc.gestionengins.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class DetailsPanne implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typePagne;

    private String observation;

    private String statut="NF";

    @ManyToOne
    @JoinColumn(name = "panne_id")
    private Panne panne;

    @ManyToOne
    @JoinColumn(name = "critere_id_critere")
    private Critere critere;


}
