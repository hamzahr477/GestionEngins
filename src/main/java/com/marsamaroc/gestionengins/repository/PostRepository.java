package com.marsamaroc.gestionengins.repository;

import com.marsamaroc.gestionengins.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource(path = "posts")
public interface PostRepository extends JpaRepository<Post,String> {
    Post getPostByCodePost(String codePost);
}
