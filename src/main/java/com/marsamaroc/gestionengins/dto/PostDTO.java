package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.Entite;
import com.marsamaroc.gestionengins.entity.Post;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Data
public class PostDTO {
    String codePost;
    public PostDTO(Post post){
        this.codePost = post.getCodePost();
    }
}
