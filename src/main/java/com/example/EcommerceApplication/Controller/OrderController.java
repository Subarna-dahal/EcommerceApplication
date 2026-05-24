package com.example.EcommerceApplication.Controller;


import com.example.EcommerceApplication.Entity.OrderEntity;
import com.example.EcommerceApplication.Exception.OrderNotFoundException;
import com.example.EcommerceApplication.Services.OrderServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderServices orderServices;

    @GetMapping()
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        List<OrderEntity> orderEntities = orderServices.getAllOrders();
        return new ResponseEntity<>(orderEntities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrder(@PathVariable Long id) {
        OrderEntity orderEntity = orderServices.findById(id).orElseThrow(
                () -> new OrderNotFoundException(id));
        return new ResponseEntity<>(orderEntity, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<OrderEntity> createOrder(@Valid @RequestBody OrderEntity order) {
        OrderEntity orderEntity = orderServices.saveOrder(order);
        return new ResponseEntity<>(orderEntity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Long id,@Valid @RequestBody OrderEntity orderDetails) {
        if (orderServices.findById(id).isPresent()) {
            OrderEntity order = orderServices.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
            order.setStatus(orderDetails.getStatus());
            order.setTotalPrice(orderDetails.getTotalPrice());
            order.setShippingAddress(orderDetails.getShippingAddress());
            order.setBillingAddress(orderDetails.getBillingAddress());
            OrderEntity newOrder = orderServices.saveOrder(order);
            return new ResponseEntity<>(newOrder, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderEntity> updateOrderStatus(@PathVariable Long id,@Valid @RequestBody String status) {
        OrderEntity order = orderServices.findById(id).orElseThrow(
                () -> new OrderNotFoundException(id));
        order.setStatus(status);
        OrderEntity updateOrder = orderServices.saveOrder(order);
         return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<OrderEntity> deleteOrder(@PathVariable Long id) {
        if (orderServices.findById(id).isPresent()) {
            orderServices.deleteOrder(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
