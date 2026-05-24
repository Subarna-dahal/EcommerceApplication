package com.example.EcommerceApplication.Exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id){
        super("Product Not Found with id:"+id);
    }
    public ProductNotFoundException(String name){

        super("Product Not Found with name:"+name);
    }
}
