package com.project.shopapp.controller;
import com.project.shopapp.dto.request.CategoryDTO;
import com.project.shopapp.dto.response.ResponseData;
import com.project.shopapp.dto.response.ResponseError;
import com.project.shopapp.model.Category;
import com.project.shopapp.model.Order;
import com.project.shopapp.service.impl.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseData<Long> createCategory(@Valid @RequestBody CategoryDTO category) {
        log.info("Request create category = {}", category.getName());
        try {
            long cateId = categoryService.createCategory(category);
            return new ResponseData<>(HttpStatus.CREATED.value(), "category create successfully", cateId);
        } catch(Exception e) {
          log.error("errorMessage = {}", e.getMessage(), e.getCause());
          return new ResponseError(HttpStatus.BAD_REQUEST.value(), "add category fail");
        }
    }

    @PutMapping("/{cateId}")
    public ResponseData<?> updateCategory(@Valid @PathVariable("cateId") Long id, @Valid @RequestBody CategoryDTO category) {
        log.info("Request update category = {}", category.getName());
        try {
            categoryService.updateCategory(id, category);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "category update successfully with id = " + id);
        } catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "update category fail");
        }
    }

    @DeleteMapping("/{cateId}")
    public ResponseData<?> deleteCategory(@Valid @PathVariable("cateId") Long id) {
        log.info("Request delete category = {}" ,id);
        try {
            categoryService.deleteCategory(id);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "category delete successfully with id = " + id);
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "delete category fail");
        }
    }

    @GetMapping("/{cateId}")
    public ResponseData<Category> getCategory(@Valid @PathVariable("cateId") Long id) {
        log.info("Request get category info by cateId = {}", id);
        try {
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get category successfully with id = " + id, categoryService.getCategoryById(id));
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseData<List<Category>> getAllCategories(@RequestParam(defaultValue = "0", required = false) int page,
                                                         @RequestParam(defaultValue = "1", required = false) int limit) {
        log.info("Request get list categories");
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get list of categories successfully", categoryService.getAllCategories(page, limit));
    }
}
