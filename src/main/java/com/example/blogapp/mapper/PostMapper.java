package com.example.blogapp.mapper;


import com.example.blogapp.dto.request.PostDto;
import com.example.blogapp.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "addedDate", target = "addedDate")
    Post toPost(PostDto postDto);

    @Mapping(source = "addedDate", target = "addedDate")
//    @Mapping(source = "comments", target = "comments")
    PostDto toPostDto(Post post);
}
