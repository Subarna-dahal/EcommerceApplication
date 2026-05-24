package com.example.EcommerceApplication.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.EcommerceApplication.Entity.ProductEntity;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
        Page<ProductEntity> findAllItem(Pageable pageable);
        List<ProductEntity> findByCategoryNameAndPriceBetween(String Category,double minPrice,double maxPrice);
}
