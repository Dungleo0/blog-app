package com.example.blogapp.service.impl;

import com.example.blogapp.dto.request.PostDto;
import com.example.blogapp.dto.response.PageResponse;
import com.example.blogapp.entity.Category;
import com.example.blogapp.entity.Post;
import com.example.blogapp.entity.User;
import com.example.blogapp.exception.ResourceNotFoundException;
import com.example.blogapp.mapper.PostMapper;
import com.example.blogapp.repository.CategoryRepository;
import com.example.blogapp.repository.PostRepository;
import com.example.blogapp.repository.UserRepo;
import com.example.blogapp.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setAddedDate(new Date());
        post.setImageName(postDto.getImageName());
        Post save = postRepository.save(post);
        return PostMapper.INSTANCE.toPostDto(save);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

        postRepository.deleteById(postId);
    }

    @Override
    public PageResponse<PostDto> getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else if (sortDir.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        }

        Pageable a = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> postList = postRepository.findAll(a);

//        List<Post> content = postList.getContent();

        List<PostDto> postDto = postList.stream().map(PostMapper.INSTANCE::toPostDto).collect(Collectors.toList());

        PageResponse pageResponse = PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .lastPage(postList.isLast())
                .totalElements(postList.getTotalElements())
                .totalPages(postList.getTotalPages())
                .content(postDto)
                .build();
        return pageResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

        PostDto postDto = PostMapper.INSTANCE.toPostDto(post);
        return postDto;

    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", categoryId));

        List<Post> posts = postRepository.findByCategory(category);

        List<PostDto> postDto = posts.stream().map(postMapper.INSTANCE::toPostDto).collect(Collectors.toList());
        return postDto;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<Post> allByUser = postRepository.findByUser(user);

        List<PostDto> postDto = allByUser.stream().map(postMapper.INSTANCE::toPostDto).collect(Collectors.toList());

        return postDto;
    }

    @Override
    public List<PostDto> searchPosts(String keywords) {
        List<Post> byTitleContaining = postRepository.findByTitleContaining(keywords);

        return byTitleContaining.stream().map(postMapper::toPostDto).collect(Collectors.toList());
    }
}
