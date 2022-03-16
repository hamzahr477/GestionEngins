package com.marsamaroc.gestionengins.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Pagne implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "engin_code_engin")
    private Engin engin;

    @ManyToOne
    @JoinColumn(name = "derniere_demande_num_bci")
    private EnginAffecte dernierAffectation;

    @ManyToOne
    @JoinColumn(name = "current_demande_num_bci")
    private Demande currentDemande;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "panne_id")
    private List<DetailsPanne> detailsPanneList;

}
