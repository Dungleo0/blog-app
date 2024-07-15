package com.example.blogapp.controller;


import com.example.blogapp.constants.AppConstants;
import com.example.blogapp.dto.request.PostDto;
import com.example.blogapp.dto.response.ApiResponse;
import com.example.blogapp.dto.response.PageResponse;
import com.example.blogapp.exception.ResourceNotFoundException;
import com.example.blogapp.service.FileService;
import com.example.blogapp.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final FileService fileService;

    @Value("${project.images}")
    private String path;

    public PostController(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }

    @PostMapping("/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId
    ) {
        PostDto createPost = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity(createPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
        List<PostDto> posts = postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);

    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
        List<PostDto> posts = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);

    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        try {
            postService.deletePost(postId);
            return new ResponseEntity<>(
                    new ApiResponse("delete post successfully", true), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("delete post failed", false), HttpStatus.OK);
        }
    }

    @GetMapping("/post")
    public ResponseEntity<PageResponse<PostDto>> getPosts(
            @RequestParam(value = "pageNo", required = false, defaultValue = AppConstants.PAGE_NUMBER) Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = AppConstants.SORT_DIR) String sortDir

    ) {
        PageResponse<PostDto> posts = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Integer postId) {
        PostDto posts = postService.getPostById(postId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
        PostDto post = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword) {
        List<PostDto> result = postService.searchPosts(keyword);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image
            , @PathVariable Integer postId) throws IOException {

        PostDto postById = postService.getPostById(postId);

        String fileName = fileService.uploadFile(path, image);

        postById.setImageName(fileName);

        PostDto updatePost = postService.updatePost(postById, postId);

        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream responseStream = this.fileService.getResource(path, imageName);
        StreamUtils.copy(responseStream, response.getOutputStream());

    }
}
