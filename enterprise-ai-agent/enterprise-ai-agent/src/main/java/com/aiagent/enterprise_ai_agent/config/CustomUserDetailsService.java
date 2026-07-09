package com.aiagent.enterprise_ai_agent.config;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.aiagent.enterprise_ai_agent.entity.User;
import com.aiagent.enterprise_ai_agent.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(
            String email)
            throws UsernameNotFoundException {

        User user = repository.findByEmail(email)

                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"));

        return org.springframework.security.core.userdetails.User

                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();

    }

}