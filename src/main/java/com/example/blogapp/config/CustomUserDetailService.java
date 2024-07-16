package com.example.blogapp.config;

import com.example.blogapp.entity.User;
import com.example.blogapp.exception.ResourceNotFoundException;
import com.example.blogapp.repository.UserRepo;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Configuration
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User byEmail = userRepo.findByUsername(username);
        if (byEmail == null) {
            throw new ResourceNotFoundException("User ", "username", 401);
        }
        return byEmail;
//        return User.builder()
//                .email(byEmail.getEmail())
//                .password(byEmail.getPassword())
//                .roles(byEmail.getRoles())
//                .build();
    }
}
