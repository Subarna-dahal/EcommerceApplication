package com.example.EcommerceApplication.Services;

import com.example.EcommerceApplication.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.EcommerceApplication.Entity.ProductEntity;

import java.util.List;
import java.util.Optional;
@Service
public class ProductServices {
    @Autowired
    private ProductRepository productRepository;

    public ProductEntity saveEntry(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    public List<ProductEntity> getAll() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> findById(long id) {
        return productRepository.findById(id);
    }
     public void deleteById(long id){
        productRepository.deleteById(id);
     }

}
