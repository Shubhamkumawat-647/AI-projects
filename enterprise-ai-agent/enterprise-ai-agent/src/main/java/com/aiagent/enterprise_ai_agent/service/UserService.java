package com.aiagent.enterprise_ai_agent.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.aiagent.enterprise_ai_agent.entity.User;
import com.aiagent.enterprise_ai_agent.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Returns currently logged-in user
     */
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
            !authentication.isAuthenticated()) {

            throw new RuntimeException("User not authenticated");
        }

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        return userRepository
                .findByEmail(userDetails.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

}