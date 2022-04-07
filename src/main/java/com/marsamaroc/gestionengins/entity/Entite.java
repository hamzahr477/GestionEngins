package com.marsamaroc.gestionengins.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Entite implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String entite;
    @ManyToMany
    @JoinTable( name = "T_Post_Entite_Associations",
            joinColumns = @JoinColumn( name = "id_entite" ),
            inverseJoinColumns = @JoinColumn( name = "id_posts" ) )
    List<Post> posts;
    @OneToMany(mappedBy="entite")
    List<Utilisateur> utilisateurList;
    @OneToMany(mappedBy="entite")
    List<Demande> demandeList;

}
