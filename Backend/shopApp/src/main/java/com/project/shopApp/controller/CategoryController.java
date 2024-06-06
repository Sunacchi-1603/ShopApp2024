package com.project.shopApp.controller;

import com.project.shopApp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    // hiển thị tất cả category
    @GetMapping("")
    public ResponseEntity<String> getAllCategory(){
        return ResponseEntity.ok("123");
    }
    @PostMapping("")
    public ResponseEntity<String> inserCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result
            ){
        if(result.hasErrors()){
            result.getFieldErrors().stream().map(FieldError::getField).toList();

        }        return ResponseEntity.ok("This is category");
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id){
        return ResponseEntity.ok("This is update category" + id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok("This is delete category" + id);
    }
}
