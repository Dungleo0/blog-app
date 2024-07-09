package com.example.blogapp.service.impl;

import com.example.blogapp.dto.request.PostDto;
import com.example.blogapp.entity.Category;
import com.example.blogapp.entity.Post;
import com.example.blogapp.entity.User;
import com.example.blogapp.exception.ResourceNotFoundException;
import com.example.blogapp.mapper.PostMapper;
import com.example.blogapp.repository.CategoryRepository;
import com.example.blogapp.repository.PostRepository;
import com.example.blogapp.repository.UserRepo;
import com.example.blogapp.service.PostService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final UserRepo userRepo;

    private final CategoryRepository categoryRepository;

    public PostServiceImpl(
            PostRepository postRepository,
            PostMapper postMapper,
            UserRepo userRepo,
            CategoryRepository categoryRepository
    ) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepo = userRepo;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

        Post post = postMapper.INSTANCE.toPost(postDto);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        postRepository.save(post);
        return postMapper.INSTANCE.toPostDto(post);
    }

    @Override
    public Post updatePost(PostDto postDto, Integer postId) {
        return null;
    }

    @Override
    public void deletePost(Integer postId) {

    }

    @Override
    public List<Post> getAllPosts() {
        return null;
    }

    @Override
    public Post getPostById(Integer postId) {
        return null;
    }

    @Override
    public List<Post> getPostsByCategory(Integer categoryId) {
        return null;
    }

    @Override
    public List<Post> getPostsByUser(Integer userId) {
        return null;
    }

    @Override
    public List<Post> searchPosts(String keywords) {
        return null;
    }
}
