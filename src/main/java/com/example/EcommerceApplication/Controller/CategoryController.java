package com.example.EcommerceApplication.Controller;


import com.example.EcommerceApplication.Entity.CategoryEntity;
import com.example.EcommerceApplication.Services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryServices categoryServices;

    @GetMapping()
    public ResponseEntity<List<CategoryEntity>> getCategory() {
        List<CategoryEntity> categoryEntities = categoryServices.getAll();
        return new ResponseEntity<>(categoryEntities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity> getCategoryByID(@PathVariable Long id) {
        CategoryEntity category = categoryServices.findByID(id).
                orElseThrow(() -> new NoSuchElementException("Category not found with id:" + id));
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CategoryEntity> saveCategory(@RequestBody CategoryEntity categoryEntity) {
        CategoryEntity category = categoryServices.saveEntry(categoryEntity);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryEntity> updateById(@PathVariable Long id, @RequestBody CategoryEntity categoryEntity) {
        CategoryEntity category = categoryServices.findByID(id).orElseThrow(
                () -> new NoSuchElementException("Could not found with id: " + id));
        category.setName(categoryEntity.getName());
        category.setDescription(categoryEntity.getDescription());
        CategoryEntity updated = categoryServices.saveEntry(category);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (categoryServices.findByID(id).isPresent()) {
            categoryServices.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}