package com.example.EcommerceApplication.Exception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(String productName, int availabe, int requested){
        super("Only "+availabe+" Units of "+productName+" in Stock, Cannnot order"+requested);
    }
}
