package com.example.EcommerceApplication.Services;

import com.example.EcommerceApplication.Entity.*;
import com.example.EcommerceApplication.Exception.ProductNotFoundException;
import com.example.EcommerceApplication.Exception.UserNotFoundException;
import com.example.EcommerceApplication.Repository.CartItemRepository;
import com.example.EcommerceApplication.Repository.CartRepository;
import com.example.EcommerceApplication.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServices {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private OrderServices orderServices;

    public CartEntity getUserCart(String email) {
        UserEntity user = userServices.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    public CartEntity addProduct(String email, Long productId, int quantity) {
        CartEntity cart = getUserCart(email);
        ProductEntity product = productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException(productId));
        CartItemsEntity item = new CartItemsEntity();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);
        cart.getItems().add(item);
        cartItemRepository.save(item);
        return cartRepository.save(cart);
    }

    public CartEntity updateCart(String email, Long itemId, int quantity) {
        CartItemsEntity items = cartItemRepository.findById(itemId).orElseThrow(
                () -> new RuntimeException("Item Not found"));

        if (!items.getCart().getUser().equals(email)) {
            throw new RuntimeException("Not your cart Item");
        }
        items.setQuantity(quantity);
        cartItemRepository.save(items);
        return items.getCart();

    }


    public void removeCartItem(String email, Long itemId) {
        CartItemsEntity items = cartItemRepository.findById(itemId).orElseThrow(() ->
                new RuntimeException("Item not found"));
        if (!items.getCart().getUser().equals(email)) {
            throw new RuntimeException("Not you cart Item");

        }
        cartItemRepository.delete(items);

    }

    public OrderEntity checkout(String email) {
        CartEntity cart = getUserCart(email);
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is Empty");
        }
        OrderEntity order = new OrderEntity();
        order.setUser(cart.getUser());
        order.setStatus("PENDING");
        order.setTotalPrice(cart.getItems().stream().mapToDouble(
                i -> i.getProduct().getPrice() * i.getQuantity()).sum());
        cart.getItems().clear();
        cartRepository.save(cart);
        return orderServices.saveOrder(order);
    }
}

