package com.aiagent.enterprise_ai_agent.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aiagent.enterprise_ai_agent.config.DTO.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(
            ResourceNotFoundException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Object>> handleUnauthorized(
            UnauthorizedException ex){

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(
            Exception ex){

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}