package com.example.EcommerceApplication.Exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("No user found with id:"+id);
    }
    public UserNotFoundException(String email){
        super("No user found with this email:"+email);
    }
}
