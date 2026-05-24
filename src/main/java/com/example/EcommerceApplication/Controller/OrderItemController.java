package com.example.EcommerceApplication.Controller;


import com.example.EcommerceApplication.Entity.OrderItemEntity;
import com.example.EcommerceApplication.Exception.InvalidOrderEcxeption;
import com.example.EcommerceApplication.Exception.OrderNotFoundException;
import com.example.EcommerceApplication.Services.OrderItemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemServices orderItemServices;

    @GetMapping
    public ResponseEntity<List<OrderItemEntity>> getAllOrderItems() {
        List<OrderItemEntity> orderItem= orderItemServices.getAllOrderItem();
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemEntity> getOrderItem(@PathVariable Long id) {
        OrderItemEntity orderItem=orderItemServices.findOrderItemById(id).orElseThrow(
                ()->new InvalidOrderEcxeption("Invalid order item"));
        return new ResponseEntity<>(orderItem,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderItemEntity> createOrderItem(@RequestBody OrderItemEntity item) {
        OrderItemEntity order= orderItemServices.saveOrderItem(item);
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemEntity> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemEntity itemDetails) {
        if (orderItemServices.findOrderItemById(id).isPresent()){
            OrderItemEntity item = orderItemServices.findOrderItemById(id).orElseThrow(
                    ()->new OrderNotFoundException(id));
            item.setQuantity(itemDetails.getQuantity());
            item.setPrice(itemDetails.getPrice());
            OrderItemEntity updatedItem=orderItemServices.saveOrderItem(item);
            return new ResponseEntity<>(updatedItem,HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderItemEntity> deleteOrderItem(@PathVariable Long id) {
        if(orderItemServices.findOrderItemById(id).isPresent()){
            orderItemServices.deleteOrderItemById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
