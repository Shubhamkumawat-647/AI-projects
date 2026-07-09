package com.aiagent.enterprise_ai_agent.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aiagent.enterprise_ai_agent.config.JwtService;
import com.aiagent.enterprise_ai_agent.config.DTO.AuthResponse;
import com.aiagent.enterprise_ai_agent.config.DTO.LoginRequest;
import com.aiagent.enterprise_ai_agent.config.DTO.RegisterRequest;
import com.aiagent.enterprise_ai_agent.entity.Role;
import com.aiagent.enterprise_ai_agent.entity.User;
import com.aiagent.enterprise_ai_agent.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered.");
        }

        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(Role.USER);

        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return "User Registered Successfully.";

    }
    
    public AuthResponse login(LoginRequest request){

        User user = userRepository

                .findByEmail(request.getEmail())

                .orElseThrow(() ->
                        new RuntimeException("Invalid Email"));

        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())){

            throw new RuntimeException(
                    "Invalid Password");

        }

        String token =
                jwtService.generateToken(
                        user.getEmail());

        return new AuthResponse(token);

    }

}