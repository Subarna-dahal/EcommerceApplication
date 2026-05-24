package com.example.EcommerceApplication.Controller;


import com.example.EcommerceApplication.Exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.EcommerceApplication.Services.ProductServices;
import com.example.EcommerceApplication.Entity.ProductEntity;

import java.nio.file.ProviderNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/products")   // match roadmap
public class ProductController {
    @Autowired
    private ProductServices productService;


    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllData() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProduct(@PathVariable Long id) {
        ProductEntity product = productService.findById(id).orElseThrow(
                ()->new ProductNotFoundException(id));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductEntity> addEntity(@RequestBody ProductEntity productEntity) {
        ProductEntity saved = productService.saveEntry(productEntity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateData(@PathVariable Long id, @RequestBody ProductEntity productEntity) {
        ProductEntity product = productService.findById(id).orElseThrow(()->
                new ProductNotFoundException(id));
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setPrice(productEntity.getPrice());
        product.setStock(productEntity.getStock());
        product.setImage(productEntity.getImage());
        ProductEntity updated = productService.saveEntry(product);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable Long id) {
        if(productService.findById(id).isPresent()){
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
