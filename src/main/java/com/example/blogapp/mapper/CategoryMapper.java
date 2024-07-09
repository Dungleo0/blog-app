package com.example.blogapp.mapper;


import com.example.blogapp.dto.request.CategoryDto;
import com.example.blogapp.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);
}
