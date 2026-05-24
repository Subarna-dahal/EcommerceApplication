package com.example.EcommerceApplication.Controller;


import com.example.EcommerceApplication.Entity.UserEntity;
import com.example.EcommerceApplication.Exception.UserNotFoundException;
import com.example.EcommerceApplication.Services.UserServices;
import jakarta.validation.Valid;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping()
    public ResponseEntity<List<UserEntity>> getUserData() {
        List<UserEntity> data = userServices.getData();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserEntity>> getUserById(@PathVariable Long id) {
        Optional<UserEntity> userdata = userServices.getUserDataById(id);
        return userdata.isPresent()
                ? new ResponseEntity<>(userdata, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> UpdateData( @Valid  @RequestBody UserEntity userEntity) {
        if (userServices.existsByEmail(userEntity.getEmail())) {
            throw new UserNotFoundException(userEntity.getEmail());
        }

        UserEntity update = userServices.saveEntry(userEntity);
        return new ResponseEntity<>(update, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> UpdateById(@PathVariable Long id,@Valid @RequestBody UserEntity userEntity) {
        UserEntity Userdata = userServices.getUserDataById(id).orElseThrow(
                ()->new UserNotFoundException(id));
            UserEntity existUserData;
        existUserData = Userdata;
        existUserData.setName(userEntity.getName());
            existUserData.setEmail(userEntity.getEmail());
            existUserData.setPassword(userEntity.getPassword());
            existUserData.setPhone(userEntity.getPhone());
            existUserData.setRole(userEntity.getRole());
            existUserData.setActive(userEntity.isActive());
            UserEntity updateData = userServices.saveEntry(existUserData);
            return new ResponseEntity<>(updateData, HttpStatus.OK);
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> deleteUserById(@PathVariable Long id) {
        UserEntity user = userServices.getUserDataById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userServices.deleteByUserId(id);
        return ResponseEntity.noContent().build();
    }
}
