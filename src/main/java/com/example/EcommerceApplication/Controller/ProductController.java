package com.example.EcommerceApplication.Controller;


import com.example.EcommerceApplication.Exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.EcommerceApplication.Services.ProductServices;
import com.example.EcommerceApplication.Entity.ProductEntity;

import java.nio.file.ProviderNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductServices productService;


    @GetMapping
    public ResponseEntity<Page<ProductEntity>> getAllProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> products = productService.getAll(pageable);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProduct(@PathVariable Long id) {
        ProductEntity product = productService.findById(id).orElseThrow(
                () -> new ProductNotFoundException(id));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductEntity>> filterProduct(
            @RequestParam String category,
            @RequestParam double minPrice,
            @RequestParam double maxPrice,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size
    ) {
        Pageable pageable=PageRequest.of(page,size);
        List<ProductEntity> filterProduct = productService.filter(category, minPrice, maxPrice,pageable);
        return new ResponseEntity<>(filterProduct, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductEntity> addEntity(@RequestBody ProductEntity productEntity) {
        ProductEntity saved = productService.saveEntry(productEntity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateData(@PathVariable Long id, @RequestBody ProductEntity productEntity) {
        ProductEntity product = productService.findById(id).orElseThrow(() ->
                new ProductNotFoundException(id));
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setPrice(productEntity.getPrice());
        product.setStock(productEntity.getStock());
        product.setImage(productEntity.getImage());
        ProductEntity updated = productService.saveEntry(product);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable Long id) {
        if (productService.findById(id).isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
