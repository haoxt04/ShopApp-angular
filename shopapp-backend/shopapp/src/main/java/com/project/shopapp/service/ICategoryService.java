package com.project.shopapp.service;

import com.project.shopapp.model.Category;

import java.util.List;

public interface ICategoryService {

    Category createCategory(Category category);

    Category getCategory(long id);

    List<Category> getAllCategories();

    Category updateCategory(long id, Category category);

    void deleteCategory(long id);
}
