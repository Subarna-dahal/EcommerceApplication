package com.example.EcommerceApplication.Services;

import com.example.EcommerceApplication.Entity.Role;
import com.example.EcommerceApplication.Entity.UserEntity;
import com.example.EcommerceApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity saveEntry(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    public List<UserEntity> getData() {
        return  userRepository.findAll();
    }

    public Optional<UserEntity> getUserDataById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteByUserId(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByEmail(String email){
       return userRepository.existsByEmail(email);
    }
    public Optional<UserEntity> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public UserEntity registerGoogleUser(String email, String name){
        UserEntity user=new UserEntity();
        user.setEmail(email);
        user.setName(name);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode("google_login"));
        return userRepository.save(user);

    }

}
