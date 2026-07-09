package com.aiagent.enterprise_ai_agent.controller;

import org.springframework.web.bind.annotation.*;

import com.aiagent.enterprise_ai_agent.config.DTO.AuthResponse;
import com.aiagent.enterprise_ai_agent.config.DTO.LoginRequest;
import com.aiagent.enterprise_ai_agent.config.DTO.RegisterRequest;
import com.aiagent.enterprise_ai_agent.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(
            @Valid
            @RequestBody RegisterRequest request) {

        return authService.register(request);

    }
    @PostMapping("/login")

    public AuthResponse login(

            @RequestBody

            LoginRequest request){

        return authService.login(request);

    }

}