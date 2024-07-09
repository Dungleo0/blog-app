package com.example.blogapp.mapper;

import com.example.blogapp.dto.request.UserDto;
import com.example.blogapp.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userDto);

    UserDto toUserDto(User user);
}
