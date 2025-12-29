package com.example.library.util;

import com.example.library.entity.Books;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {


        public static ResponseEntity<Object> success(Object data, String message) {
            return ResponseEntity.ok(new Response(true, message, data));
        }

        public static ResponseEntity<Object> created(Object data, String message) {
            return new ResponseEntity<>(new Response(true, message, data), HttpStatus.CREATED);
        }

        public static ResponseEntity<Object> error(String message, HttpStatus status) {
            return new ResponseEntity<>(new Response(false, message, null), status);
        }
}


