package com.example.library.service.impl;

import com.example.library.dto.LoginRequest;
import com.example.library.dto.RegisterRequest;
import com.example.library.entity.User;
import com.example.library.exception.BadRequestException;
import com.example.library.repository.UserRepository;
import com.example.library.service.AuthService;
import com.example.library.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    // Simple password encoder implementation to avoid adding full Spring Security to the project
    private String encodePassword(String rawPassword) {
        // In a real application, use BCrypt or another strong hashing algorithm.
        // This placeholder uses a basic hash to keep the sample self-contained.
        return Integer.toHexString(rawPassword.hashCode());
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return encodePassword(rawPassword).equals(encodedPassword);
    }

    @Override
    public ResponseEntity<Object> register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encodePassword(request.getPassword()));

        User savedUser = userRepository.save(user);

        // Do not expose password in response (it's ignored by @JsonIgnore anyway)
        return ApiResponse.created(savedUser, "User registered successfully");
    }

    @Override
    public ResponseEntity<Object> login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            throw new BadRequestException("Invalid email or password");
        }

        User user = userOptional.get();

        if (!matchesPassword(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        // Basic login response; no tokens yet
        return ApiResponse.success(user, "Login successful");
    }
}


