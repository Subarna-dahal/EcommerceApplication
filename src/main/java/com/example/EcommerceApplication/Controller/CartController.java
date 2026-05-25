package com.example.EcommerceApplication.Controller;


import com.example.EcommerceApplication.Entity.CartEntity;
import com.example.EcommerceApplication.Entity.OrderEntity;
import com.example.EcommerceApplication.Services.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartServices cartServices;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<CartEntity> viewCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        CartEntity cart = cartServices.getUserCart(email);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ResponseEntity<CartEntity> addToCart(
            @RequestParam Long productId,
            @RequestBody int quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        CartEntity cart = cartServices.addProduct(email, productId, quantity);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{itemId}")
    public ResponseEntity<CartEntity> updateCard(
            @PathVariable Long itemId,
            @RequestParam int quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        CartEntity cart = cartServices.updateCart(email, itemId, quantity);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        cartServices.removeCartItem(email, itemId);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/checkout")
    public ResponseEntity<OrderEntity> checkout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        OrderEntity order = cartServices.checkout(email);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

}
