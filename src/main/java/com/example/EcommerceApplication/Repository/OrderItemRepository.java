package com.example.EcommerceApplication.Repository;

import com.example.EcommerceApplication.Entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Long> {

}
