package com.project.shopApp.services;

import com.project.shopApp.dtos.CategoryDTO;
import com.project.shopApp.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategory();
    Category updateCategory(long categoryId ,CategoryDTO categoryDto);
    void deleteCategory(long id);
}
