package com.devmountain.photocollect.controllers;

import com.devmountain.photocollect.dtos.PostDto;
import com.devmountain.photocollect.entities.Post;
import com.devmountain.photocollect.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/user/{userId}")
    public List<PostDto> getPostsByUser(@PathVariable Long userId){
        return postService.getAllPostsByUserId(userId);
    }
    @PostMapping("/user/{userId}")
    public void addPost(@RequestBody PostDto postDto, @PathVariable Long userId){
        postService.addPost(postDto, userId);
    }
    @DeleteMapping("/{postId}")
    public void deletePostById(@PathVariable Long postId){
        postService.deletePostById(postId);
    }
    @PutMapping("/${postId}")
    public void updatePost(@RequestBody PostDto postDto){
        postService.updatePostById(postDto);
    }
    @GetMapping("/{postId}")
    public Optional<PostDto> getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

}
