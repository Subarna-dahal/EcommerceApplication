package com.example.EcommerceApplication.Services;


import com.example.EcommerceApplication.Entity.OrderItemEntity;
import com.example.EcommerceApplication.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServices {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItemEntity saveOrderItem(OrderItemEntity orderItem) {

        return orderItemRepository.save(orderItem);
    }

    public List<OrderItemEntity> getAllOrderItem(){

        return  orderItemRepository.findAll();
    }

    public Optional<OrderItemEntity> findOrderItemById(Long id){

        return orderItemRepository.findById(id);
    }

    public void deleteOrderItemById(Long id){

        orderItemRepository.findById(id);
    }
}
