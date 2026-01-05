package com.example.library.service;

import com.example.library.dto.LoginRequest;
import com.example.library.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<Object> register(RegisterRequest request);

    ResponseEntity<Object> login(LoginRequest request);
}


