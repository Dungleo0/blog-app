package com.example.blogapp.controller;


import com.example.blogapp.dto.request.CommentDto;
import com.example.blogapp.dto.response.ApiResponse;
import com.example.blogapp.exception.ResourceNotFoundException;
import com.example.blogapp.service.CommentService;
import com.example.blogapp.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {


    private final CommentService commentService;

    private final PostService postService;

    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId) {
        CommentDto createComment = commentService.createComment(commentDto, postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createComment);
    }

    @DeleteMapping("/comments/{commentId}")
    private ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
        }
    }

}
