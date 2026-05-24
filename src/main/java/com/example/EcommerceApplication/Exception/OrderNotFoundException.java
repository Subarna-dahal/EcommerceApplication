package com.example.EcommerceApplication.Exception;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(Long id){
        super("Not found Order with id"+id);
    }

}
