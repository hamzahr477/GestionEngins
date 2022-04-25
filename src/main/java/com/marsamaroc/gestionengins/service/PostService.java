package com.marsamaroc.gestionengins.service;

import com.marsamaroc.gestionengins.dto.PostDTO;
import com.marsamaroc.gestionengins.entity.Post;
import com.marsamaroc.gestionengins.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService  {
    @Autowired
    PostRepository postRepository;

    public Post getById(String id) {
        return postRepository.getPostByCodePost(id);
    }

    public PostDTO savOrUpdate(Post post) {
        Post oldpost = postRepository.getPostByCodePost(post.getCodePost());
        if(oldpost!= null)  oldpost.sync(post);
        else oldpost = post;
        return new PostDTO(postRepository.save(oldpost));
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }
}
