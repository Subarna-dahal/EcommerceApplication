package com.example.EcommerceApplication.Exception;

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String Message){
        super(Message);
    }
}
