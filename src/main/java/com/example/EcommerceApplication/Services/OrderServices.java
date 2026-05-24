package com.example.EcommerceApplication.Services;


import com.example.EcommerceApplication.Entity.OrderEntity;
import com.example.EcommerceApplication.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServices {
    @Autowired
    private OrderRepository orderRepository;

    public OrderEntity saveOrder(OrderEntity order) {
        return orderRepository.save(order);
    }

    public List<OrderEntity> getAllOrders() {

        return orderRepository.findAll();
    }

    public Optional<OrderEntity> findById(Long id) {

        return orderRepository.findById(id);
    }

    public void deleteOrder(Long id) {

        orderRepository.deleteById(id);
    }
}
