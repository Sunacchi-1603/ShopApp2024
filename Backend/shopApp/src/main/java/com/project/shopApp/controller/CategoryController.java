package com.project.shopApp.controller;

import com.project.shopApp.dtos.CategoryDTO;
import com.project.shopApp.models.Category;
import com.project.shopApp.responses.CategoryResponse;
import com.project.shopApp.responses.LoginResponse;
import com.project.shopApp.responses.UpdateCategoryResponse;
import com.project.shopApp.services.CategoryService;
import com.project.shopApp.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    public final CategoryService categoryService;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;
    private final com.project.shopApp.components.LocalizationUtils localizationUtils;
    // hiển thị tất cả category
    //Hiện tất cả các categories
    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam("page")     int page,
            @RequestParam("limit")    int limit
    ) {
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("")
    public ResponseEntity<CategoryResponse> inserCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result
    ) {
        CategoryResponse categoryResponse = new CategoryResponse();
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getField).toList();
                categoryResponse.setMessage(errorMessages.toString());
                return ResponseEntity.badRequest().body(categoryResponse);
            }
            Category category = categoryService.createCategory(categoryDTO);
            categoryResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_SUCCESSFULLY));
            categoryResponse.setCategory(category);
            return ResponseEntity.ok(categoryResponse);
        } catch (Exception e) {
            categoryResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(categoryResponse);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateCategoryResponse> updateCategory(
            @Valid @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO,
            HttpServletRequest request
    ) {
        categoryService.updateCategory(id, categoryDTO);
        Locale locale = localeResolver.resolveLocale((request));
        return ResponseEntity.ok(UpdateCategoryResponse.builder()
                        .mesage(messageSource.getMessage("category.update_category.update_successfully",null,locale))
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@Valid @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("This is delete categoryId = " + id);
    }
}
