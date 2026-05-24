package com.example.EcommerceApplication.Repository;

import com.example.EcommerceApplication.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<CategoryEntity,Long> {

}
