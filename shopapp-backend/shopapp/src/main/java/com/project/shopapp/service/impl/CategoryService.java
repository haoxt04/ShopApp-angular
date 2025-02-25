package com.project.shopapp.service.impl;

import com.project.shopapp.model.Category;
import com.project.shopapp.repository.CategoryRepository;
import com.project.shopapp.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(long id, Category category) {
        Category existingCategory = categoryRepository.getReferenceById(id);
        existingCategory.setName(category.getName());
        return existingCategory;
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
}
