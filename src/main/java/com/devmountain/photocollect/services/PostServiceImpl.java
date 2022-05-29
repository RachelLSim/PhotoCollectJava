package com.devmountain.photocollect.services;

import com.devmountain.photocollect.dtos.PostDto;
import com.devmountain.photocollect.entities.Post;
import com.devmountain.photocollect.entities.User;
import com.devmountain.photocollect.repositories.PostRepository;
import com.devmountain.photocollect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional
    public void addPost(PostDto postDto, Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        Post post = new Post(postDto);
        userOptional.ifPresent(post::setUser);
        postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional
    public void deletePostById(Long postId){
        Optional<Post> postOptional = postRepository.findById(postId);
        postOptional.ifPresent(post -> postRepository.delete(post));
    }

    @Override
    @Transactional
    public void updatePostById(PostDto postDto){
        Optional<Post> postOptional = postRepository.findById(postDto.getId());
        postOptional.ifPresent(post -> {
            post.setUrl(postDto.getUrl());
            post.setCaption((postDto.getCaption()));
            postRepository.saveAndFlush(post);
        });
    }

    @Override
    public List<PostDto> getAllPostsByUserId(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            List<Post> postList = postRepository.findAllByUserEquals(userOptional.get());
        return postList.stream().map(post -> new PostDto(post)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<PostDto> getPostById(Long postId){
        Optional<Post> postOptional = postRepository.findById(postId);
        if(postOptional.isPresent()){
            return Optional.of(new PostDto(postOptional.get()));
        }
        return Optional.empty();
    }
}
