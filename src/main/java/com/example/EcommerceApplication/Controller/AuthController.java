package com.example.EcommerceApplication.Controller;

import com.example.EcommerceApplication.DataTransferObject.LoginRequest;
import com.example.EcommerceApplication.Entity.UserEntity;
import com.example.EcommerceApplication.Exception.UserNotFoundException;
import com.example.EcommerceApplication.Security.JwtUtil;
import com.example.EcommerceApplication.Services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServices userServices;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register( @Valid  @RequestBody UserEntity user){
        UserEntity savedUser=userServices.saveEntry(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        final UserDetails userDetails = userServices.findByEmail(loginRequest.getEmail())
                .map(u -> new org.springframework.security.core.userdetails.User(
                        u.getEmail(), u.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole()))
                ))
                .orElseThrow(() -> new UserNotFoundException(loginRequest.getEmail()));

        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(token);
    }
}



