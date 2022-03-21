package com.marsamaroc.gestionengins.controller;
import com.marsamaroc.gestionengins.dto.PostDTO;
import com.marsamaroc.gestionengins.entity.Post;
import com.marsamaroc.gestionengins.service.PostService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;
    @PostMapping(value="/add")
    PostDTO saveOrUpdate(@RequestBody Post post){

        return postService.savOrUpdate(post);

    }
    @PutMapping(value="/")
    PostDTO put(@RequestBody Post post){

        return postService.savOrUpdate(post);

    }

    @GetMapping(value="/get")

    List<PostDTO> findAll(){

        List<PostDTO> listPostsDTO = new ArrayList<>();

        List<Post> listPosts = postService.findAll();



        for(Post post_ : listPosts) {

            listPostsDTO.add(new PostDTO(post_));

        }

        return listPostsDTO;

    }

}
