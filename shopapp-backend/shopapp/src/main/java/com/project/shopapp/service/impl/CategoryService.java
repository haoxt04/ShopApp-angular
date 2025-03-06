package com.project.shopapp.service.impl;

import com.project.shopapp.dto.request.CategoryDTO;
import com.project.shopapp.exception.ResourceNotFoundException;
import com.project.shopapp.model.Category;
import com.project.shopapp.repository.CategoryRepository;
import com.project.shopapp.service.ICategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    public Long createCategory(CategoryDTO request) {
        Category cate = Category.builder()
                .id(request.getId())
                .name(request.getName())
                .build();
        categoryRepository.save(cate);
        log.info("cate has saved");
        return cate.getId();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("cate not found"));
    }

    @Override
    public List<Category> getAllCategories(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);

        Page<Category> categories = categoryRepository.findAll(pageable);

        return categories.stream().map(cate -> Category.builder()
                .id(cate.getId())
                .name(cate.getName())
                .build()).toList();
    }

    @Override
    @Transactional
    public void updateCategory(Long id, CategoryDTO request) {
        Category cate = getCategoryById(id);
        cate.setId(request.getId());
        cate.setName(request.getName());
        categoryRepository.save(cate);
        log.info("cate updated , cateId = {}", id);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        log.info("cate deleted, cateId =  {}", id);
    }
}
