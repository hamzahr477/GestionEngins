package com.marsamaroc.gestionengins.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Post implements Serializable {
    @Id
    String codePost;
    @ManyToOne
    @JoinColumn(name = "id_entite")
    Entite entite;
    //Parametrage
    private Boolean active = true;
    private Date dateCreation;
    private Date derniereModification;
    ////

    public void sync(Post post){
        if(post == null) return;
        this.codePost  = post.getCodePost()!=null ? post.getCodePost() : this.codePost;
        this.entite  = post.getEntite()!=null ? post.getEntite() : this.entite;
    }

}
