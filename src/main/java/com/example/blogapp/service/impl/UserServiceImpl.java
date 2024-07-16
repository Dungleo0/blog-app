package com.example.blogapp.service.impl;

import com.example.blogapp.dto.request.UserDto;
import com.example.blogapp.entity.User;
import com.example.blogapp.exception.ResourceNotFoundException;
import com.example.blogapp.repository.UserRepo;
import com.example.blogapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper mapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepo.save(user);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {

        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setUsername(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());

        userRepo.save(user);

        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return mapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();

        List<UserDto> userDtos = users.stream().map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        return userDtos;

    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        userRepo.delete(user);

    }
}
