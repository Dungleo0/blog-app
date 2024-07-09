package com.example.blogapp.service;

import com.example.blogapp.dto.request.PostDto;
import com.example.blogapp.entity.Post;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    Post updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    List<Post> getAllPosts();

    Post getPostById(Integer postId);

    List<Post> getPostsByCategory(Integer categoryId);

    List<Post> getPostsByUser(Integer userId);

    List<Post> searchPosts(String keywords);
}
