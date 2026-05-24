package com.example.EcommerceApplication.Security;

import com.example.EcommerceApplication.Entity.UserEntity;
import com.example.EcommerceApplication.Exception.UserNotFoundException;
import com.example.EcommerceApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException{
        UserEntity user=userRepository.findByEmail(email).orElseThrow(
                ()->new UserNotFoundException(email));
        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(()->"Role"+user.getRole().name()));
    }
}
