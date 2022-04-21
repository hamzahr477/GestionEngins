package com.marsamaroc.gestionengins.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Post implements Serializable {
    @Id
    String codePost;
    @ManyToMany
    @JoinTable( name = "T_Post_Entite_Associations",
            joinColumns = @JoinColumn( name = "id_posts" ),
            inverseJoinColumns = @JoinColumn( name = "id_entite" ) )
    List<Entite> entites;
    boolean deleted = false;

    public void sync(Post post){
        if(post == null) return;
        this.codePost  = post.getCodePost()!=null ? post.getCodePost() : this.codePost;
        this.entites  = post.getEntites()!=null ? post.getEntites() : this.entites;
    }

}
