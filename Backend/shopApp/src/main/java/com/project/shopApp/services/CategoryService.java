package com.project.shopApp.services;

import com.project.shopApp.dtos.CategoryDTO;
import com.project.shopApp.models.Category;
import com.project.shopApp.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();
       return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("cannot find category"));
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(long categoryId, CategoryDTO categoryDto) {
        Category category = getCategoryById(categoryId);
        category.setName(categoryDto.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(long id) {
        //xoa cung
        categoryRepository.deleteById(id);
    }
}
