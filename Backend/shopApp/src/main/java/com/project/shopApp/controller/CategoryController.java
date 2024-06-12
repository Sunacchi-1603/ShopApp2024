package com.project.shopApp.controller;

import com.project.shopApp.dtos.CategoryDTO;
import com.project.shopApp.models.Category;
import com.project.shopApp.services.CategoryService;
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
    public final CategoryService categoryService;

    // hiển thị tất cả category
    @GetMapping("")
    public ResponseEntity<String> getAllCategory() {
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories + "");
    }

    @PostMapping("")
    public ResponseEntity<?> inserCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                result.getFieldErrors().stream().map(FieldError::getField).toList();
            }
            categoryService.createCategory(categoryDTO);
            return ResponseEntity.ok("success " + categoryDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(
            @Valid @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO
    ) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("This is update category" + categoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@Valid @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("This is delete categoryId = " + id);
    }
}
