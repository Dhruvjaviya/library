package com.example.library.exception;

import com.example.library.util.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @Hidden
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
        return ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @Hidden
    public ResponseEntity<Object> handleBadRequest(BadRequestException ex) {
        return ApiResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @Hidden
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {
        String error = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();
        return ApiResponse.error(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @Hidden
    public ResponseEntity<Object> handleAll(Exception ex) {
        logger.error("Unhandled exception occurred", ex);
        ex.printStackTrace();
        return ApiResponse.error("Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
