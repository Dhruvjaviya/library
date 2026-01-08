package com.example.library.service.impl;

import com.example.library.dto.LoginRequest;
import com.example.library.dto.RegisterRequest;
import com.example.library.entity.User;
import com.example.library.exception.BadRequestException;
import com.example.library.repository.UserRepository;
import com.example.library.service.AuthService;
import com.example.library.util.ApiResponse;
import com.example.library.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Simple salt added before hashing; consider moving to configuration
    private static final String PASSWORD_SALT = "library-salt-2026";

    private String encodePassword(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest((PASSWORD_SALT + rawPassword).getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Password hashing failed", e);
        }
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

        // Generate JWT with subject as user email
        String token = jwtUtil.generateToken(user.getEmail());

        Map<String, Object> payload = new HashMap<>();
        payload.put("token", token);
        payload.put("user", user);

        return ApiResponse.success(payload, "Login successful");
    }
}


