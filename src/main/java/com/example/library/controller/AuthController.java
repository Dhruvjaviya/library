package com.example.library.controller;

import com.example.library.dto.LoginRequest;
import com.example.library.dto.RegisterRequest;
import com.example.library.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = "application/json")
@Tag(name = "Authentication", description = "Authentication management APIs for user registration and login")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
            summary = "Register a new user",
            description = "Register a new user with name, email, and password. Password must be at least 6 characters long."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @Operation(
            summary = "User login",
            description = "Authenticate user with email and password. Returns a JWT token upon successful authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}


