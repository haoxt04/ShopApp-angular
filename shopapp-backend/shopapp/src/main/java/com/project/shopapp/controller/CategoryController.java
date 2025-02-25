package com.project.shopapp.controller;


import com.project.shopapp.dto.request.CategoryDTO;
import com.project.shopapp.service.impl.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<String> getAllCategories(@RequestParam(defaultValue = "0", required = false) int page,
                                                   @RequestParam(defaultValue = "0", required = false) int limit) {
        return ResponseEntity.ok(String.format("getAllCategories, page = %d, limit = %d", page, limit));
    }
    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO category, BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("This is insertCategory " + category);

    }
    @PutMapping("/{cateId}")
    public ResponseEntity<String> updateCategory(@Valid @PathVariable("cateId") Long id) {
        return ResponseEntity.ok("updateCategory with id = " + id);
    }
    @DeleteMapping("/{cateId}")
    public ResponseEntity<String> deleteCategory(@Valid @PathVariable("cateId") Long id) {
        return ResponseEntity.ok("deleteCategory with id = " + id);
    }
}
