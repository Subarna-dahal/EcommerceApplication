package com.example.EcommerceApplication.Controller;

import com.example.EcommerceApplication.DataTransferObject.GoogleLoginRequest;
import com.example.EcommerceApplication.DataTransferObject.GoogleLoginResponse;
import com.example.EcommerceApplication.Entity.UserEntity;
import com.example.EcommerceApplication.Security.GoogleTokenVerifier;
import com.example.EcommerceApplication.Security.JwtUtil;
import com.example.EcommerceApplication.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth/google")
public class GoogleAuthController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<GoogleLoginResponse> login(@RequestBody GoogleLoginRequest request) throws Exception {
        var payload = GoogleTokenVerifier.verifyToken(request.getIdToken());

        String email = payload.getEmail();
        String name = (String) payload.get("name");

        UserEntity user = userServices.findByEmail(email)
                .orElseGet(() -> userServices.registerGoogleUser(email, name));


        String token = jwtUtil.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(), user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                ));
        return ResponseEntity.ok(new GoogleLoginResponse(token, user.getEmail(), user.getRole().name()));
    }
}

