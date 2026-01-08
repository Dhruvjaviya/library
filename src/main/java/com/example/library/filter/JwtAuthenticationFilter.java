package com.example.library.filter;

import com.example.library.util.JwtUtil;
import com.example.library.util.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private static final List<String> EXACT_MATCH_ENDPOINTS = List.of(
            "/login",
            "/register"
    );

    private static final List<String> PREFIX_MATCH_ENDPOINTS = List.of(
            "/swagger-ui",
            "/api-docs",
            "/v3/api-docs",
            "/swagger-resources",
            "/webjars"
    );

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (isOpenEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            unauthorized(response, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            unauthorized(response, "Invalid or expired token");
            return;
        }

        // Token is valid; proceed
        filterChain.doFilter(request, response);
    }

    private boolean isOpenEndpoint(String path) {
        // Check exact matches (for /login, /register)
        if (EXACT_MATCH_ENDPOINTS.stream().anyMatch(path::equalsIgnoreCase)) {
            return true;
        }
        // Check prefix matches (for Swagger UI paths)
        return PREFIX_MATCH_ENDPOINTS.stream().anyMatch(path::startsWith);
    }

    private void unauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        Response body = new Response(false, message, null);
        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
}


