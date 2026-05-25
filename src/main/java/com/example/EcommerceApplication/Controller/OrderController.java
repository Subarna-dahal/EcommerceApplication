package com.example.EcommerceApplication.Controller;

import com.example.EcommerceApplication.DataTransferObject.StatusUpdateRequest;
import com.example.EcommerceApplication.Entity.OrderEntity;
import com.example.EcommerceApplication.Entity.UserEntity;
import com.example.EcommerceApplication.Exception.OrderNotFoundException;
import com.example.EcommerceApplication.Services.OrderServices;
import com.example.EcommerceApplication.Services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @Autowired
    private UserServices userServices;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        List<OrderEntity> orderEntities = orderServices.getAllOrders();
        return new ResponseEntity<>(orderEntities, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrder(@PathVariable Long id) {
        OrderEntity orderEntity = orderServices.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        if (!orderEntity.getUser().getEmail().equals(currentUserEmail)) {
            throw new AccessDeniedException("You are not allowed to access this order");
        }

        return new ResponseEntity<>(orderEntity, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(@Valid @RequestBody OrderEntity order) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        UserEntity currentUser = userServices.findByEmail(currentUserEmail)
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        order.setUser(currentUser);

        OrderEntity savedOrder = orderServices.saveOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Long id,
                                                   @Valid @RequestBody OrderEntity orderDetails) {
        OrderEntity order = orderServices.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        if (!order.getUser().getEmail().equals(currentUserEmail)) {
            throw new AccessDeniedException("You cannot modify someone else’s order");
        }

        order.setStatus(orderDetails.getStatus());
        order.setTotalPrice(orderDetails.getTotalPrice());
        order.setShippingAddress(orderDetails.getShippingAddress());
        order.setBillingAddress(orderDetails.getBillingAddress());

        OrderEntity updatedOrder = orderServices.saveOrder(order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderEntity> updateOrderStatus(@PathVariable Long id,
                                                         @Valid @RequestBody StatusUpdateRequest status) {
        OrderEntity order = orderServices.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        order.setStatus(status.getStatus());
        OrderEntity updatedOrder = orderServices.saveOrder(order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        OrderEntity order = orderServices.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!order.getUser().getEmail().equals(currentUserEmail) && !isAdmin) {
            throw new AccessDeniedException("You cannot delete someone else’s order");
        }

        orderServices.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
