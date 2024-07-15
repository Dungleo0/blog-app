package com.example.blogapp.service;

import com.example.blogapp.dto.request.PostDto;
import com.example.blogapp.dto.response.PageResponse;
import com.example.blogapp.entity.Post;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PageResponse<PostDto> getAllPosts(Integer pageNo, Integer pageSize,String sortBy,String sortDir);

    PostDto getPostById(Integer postId);

    List<PostDto> getPostsByCategory(Integer categoryId);

    List<PostDto> getPostsByUser(Integer userId);

    List<PostDto> searchPosts(String keywords);
}
