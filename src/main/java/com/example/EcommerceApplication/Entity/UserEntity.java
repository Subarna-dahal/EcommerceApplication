package com.example.EcommerceApplication.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;
    @Column(unique = true)
    @Email(message = "Please write email in Standard format")
    @NotBlank
    private String email;

    @NotBlank
    private String password;
    private String phone;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isActive;






}
