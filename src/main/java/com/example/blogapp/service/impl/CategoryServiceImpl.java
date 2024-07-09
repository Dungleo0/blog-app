package com.example.blogapp.service.impl;

import com.example.blogapp.dto.request.CategoryDto;
import com.example.blogapp.entity.Category;
import com.example.blogapp.exception.ResourceNotFoundException;
import com.example.blogapp.mapper.CategoryMapper;
import com.example.blogapp.repository.CategoryRepository;
import com.example.blogapp.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.INSTANCE.toCategory(categoryDto);
        categoryRepository.save(category);


        return categoryMapper.INSTANCE.toCategoryDto(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("can't not find category", "categoryId", categoryId);
        }
        return categoryMapper.INSTANCE.toCategoryDto(category.get());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found", "Category", categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        categoryRepository.save(category);

        return categoryMapper.INSTANCE.toCategoryDto(category);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found", "Category", categoryId));
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> category = categoryRepository.findAll();

        return category.stream().map(cate -> categoryMapper.INSTANCE.toCategoryDto(cate)).collect(Collectors.toList());
    }
}
