package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.dto.PostDTO;
import com.marsamaroc.gestionengins.entity.Post;
import org.springframework.stereotype.Service;

public interface PostService {
    Post getById(Long id);
    PostDTO savOrUpdate(Post post);
}

