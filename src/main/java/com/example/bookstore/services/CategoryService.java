package com.example.bookstore.services;

import com.example.bookstore.models.Category;
import com.example.bookstore.models.dto.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(String id);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, String id);
    void deleteCategory(String id);
}
