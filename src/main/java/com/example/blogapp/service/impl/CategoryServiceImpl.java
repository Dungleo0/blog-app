package com.example.blogapp.service.impl;

import com.example.blogapp.dto.request.CategoryDto;
import com.example.blogapp.entity.Category;
import com.example.blogapp.exception.ResourceNotFoundException;
import com.example.blogapp.repository.CategoryRepository;
import com.example.blogapp.service.CategoryService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        categoryRepository.save(category);


        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("can't not find category", "categoryId", categoryId);
        }
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        return null;
    }

    @Override
    public void deleteCategory(Integer categoryId) {

    }

    @Override
    public List<CategoryDto> getCategories() {
        return null;
    }
}
