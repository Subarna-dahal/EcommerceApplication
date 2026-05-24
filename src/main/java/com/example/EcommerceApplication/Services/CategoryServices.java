package com.example.EcommerceApplication.Services;


import com.example.EcommerceApplication.Entity.CategoryEntity;
import com.example.EcommerceApplication.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServices {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity saveEntry(CategoryEntity categoryEntity) {
        return categoryRepository.save(categoryEntity);
    }

    public List<CategoryEntity> getAll() {
        return categoryRepository.findAll();

    }

    public Optional<CategoryEntity> findByID(Long id) {
        return categoryRepository.findById(id);

    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }


}