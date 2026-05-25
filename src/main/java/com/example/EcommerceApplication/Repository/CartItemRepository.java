package com.example.EcommerceApplication.Repository;

import com.example.EcommerceApplication.Entity.CartItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemsEntity,Long> {
}
