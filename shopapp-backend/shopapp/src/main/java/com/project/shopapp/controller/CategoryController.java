package com.project.shopapp.controller;


import com.project.shopapp.dto.request.CategoryDTO;
import com.project.shopapp.dto.response.ResponseData;
import com.project.shopapp.service.impl.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private CategoryService categoryService;

    @PostMapping("")
    public ResponseData<Integer> createCategory(@Valid @RequestBody CategoryDTO category) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "category create successfully", 1);
    }

    @PutMapping("/{cateId}")
    public ResponseData<?> updateCategory(@Valid @PathVariable("cateId") Long id) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "category update successfully with id = " + id);
    }

    @DeleteMapping("/{cateId}")
    public ResponseData<?> deleteCategory(@Valid @PathVariable("cateId") Long id) {
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "category delete successfully with id = " + id);
    }

    @GetMapping("/{cateId}")
    public ResponseData<CategoryDTO> getCategory(@Valid @PathVariable("cateId") Long id) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get category successfully with id = " + id, null);
    }

    @GetMapping("/list")
    public ResponseData<List<CategoryDTO>> getAllCategories(@RequestParam(defaultValue = "0", required = false) int page,
                                            @RequestParam(defaultValue = "0", required = false) int limit) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get list of categories successfully", null);
    }
}
