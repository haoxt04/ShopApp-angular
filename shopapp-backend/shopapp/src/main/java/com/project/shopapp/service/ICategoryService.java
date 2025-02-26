package com.project.shopapp.service;

import com.project.shopapp.dto.request.CategoryDTO;
import com.project.shopapp.model.Category;

import java.util.List;

public interface ICategoryService {

    Long createCategory(CategoryDTO category);

    Category getCategoryById(Long id);

    List<Category> getAllCategories(int page, int limit);

    void updateCategory(Long id, CategoryDTO category);

    void deleteCategory(Long id);
}
