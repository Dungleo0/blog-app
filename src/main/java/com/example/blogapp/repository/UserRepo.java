package com.example.blogapp.repository;

import com.example.blogapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
