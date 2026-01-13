package com.example.bookstore.controllers;

import com.example.bookstore.models.APIResponse;
import com.example.bookstore.models.Category;
import com.example.bookstore.models.dto.CategoryDTO;
import com.example.bookstore.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories") // Base URL cho module Category
@RequiredArgsConstructor // Để tự động inject CategoryService
public class CategoryController {

    private final CategoryService categoryService;

    // --- Lấy tất cả Category ---
    // GET /api/categories
    @GetMapping
    public ResponseEntity<APIResponse<List<CategoryDTO>>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        APIResponse<List<CategoryDTO>> response = new APIResponse<>(
                "success",
                "Categories retrieved successfully",
                categories,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }
    // --- Lấy Category theo ID ---
    // GET /api/categories/{id}
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDTO>> getCategoryById(@PathVariable String id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        APIResponse<CategoryDTO> response = new APIResponse<>(
                "success",
                "Category retrieved successfully",
                category,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    // --- Tạo mới Category ---
    // POST /api/categories
    @PostMapping
    public ResponseEntity<APIResponse<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO created = categoryService.createCategory(categoryDTO);
        APIResponse<CategoryDTO> response = new APIResponse<>(
                "success",
                "Category created successfully",
                created,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // --- Cập nhật Category ---
    // PUT /api/categories/{id}
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDTO>> updateCategory(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updated = categoryService.updateCategory(categoryDTO, id);
        APIResponse<CategoryDTO> response = new APIResponse<>(
                "success",
                "Category updated successfully",
                updated,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    // --- Xóa Category ---
    // DELETE /api/categories/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Category deleted successfully",
                null,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }
}