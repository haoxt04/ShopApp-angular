package com.project.shopapp.controller;
import com.project.shopapp.component.LocalizationUtils;
import com.project.shopapp.dto.request.CategoryDTO;
import com.project.shopapp.dto.response.LoginResponse;
import com.project.shopapp.dto.response.ResponseData;
import com.project.shopapp.dto.response.ResponseError;
import com.project.shopapp.dto.response.UpdateCategoryResponse;
import com.project.shopapp.model.Category;
import com.project.shopapp.model.Order;
import com.project.shopapp.service.impl.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;
    private final LocalizationUtils localizationUtils;

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
    public ResponseEntity<UpdateCategoryResponse> updateCategory(@Valid @PathVariable("cateId") Long id, @Valid @RequestBody CategoryDTO category, HttpServletRequest request) {
        log.info("Request update category = {}", category.getName());
        try {
            categoryService.updateCategory(id, category);
            Locale locale = localeResolver.resolveLocale(request);
            return ResponseEntity.ok(UpdateCategoryResponse.builder()
                            .message(messageSource.getMessage("category.update_category.update_successfully", null, locale))

                    .build());
        } catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return ResponseEntity.badRequest().body(
                    UpdateCategoryResponse.builder()
                            .message(e.getMessage())
                            .build()
            );
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

    @GetMapping("")
    public ResponseData<List<Category>> getAllCategories(@RequestParam(defaultValue = "0", required = false) int page,
                                                         @RequestParam(defaultValue = "1", required = false) int limit) {
        log.info("Request get list categories");
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get list of categories successfully", categoryService.getAllCategories(page, limit));
    }
}
