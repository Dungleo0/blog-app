package com.example.blogapp.repository;

import com.example.blogapp.entity.Category;
import com.example.blogapp.entity.Post;
import com.example.blogapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByUser(User user);
    List<Post> findAllByCategory(Category category);
}
