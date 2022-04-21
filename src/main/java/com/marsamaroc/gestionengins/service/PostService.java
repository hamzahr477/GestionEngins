package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.dto.PostDTO;
import com.marsamaroc.gestionengins.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {
    Post getById(String id);
    PostDTO savOrUpdate(Post post);

    List<Post> findAll();
}

