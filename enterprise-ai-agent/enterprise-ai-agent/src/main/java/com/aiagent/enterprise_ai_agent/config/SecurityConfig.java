package com.aiagent.enterprise_ai_agent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aiagent.enterprise_ai_agent.service.AuthService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtFilter;

	private final CustomUserDetailsService customUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

                .csrf(csrf->csrf.disable())

                .authorizeHttpRequests(auth->auth

                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**")

                        .permitAll()

                        .anyRequest()

                        .authenticated()

                )

                .sessionManagement(session->

                        session.sessionCreationPolicy(

                                SessionCreationPolicy.STATELESS

                        )

                )

                .authenticationProvider(authenticationProvider())

                .addFilterBefore(

                        jwtFilter,

                        UsernamePasswordAuthenticationFilter.class

                );

        return http.build();

    }
    @Bean
    AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(

                customUserDetailsService

        );

        provider.setPasswordEncoder(

                passwordEncoder()

        );

        return provider;

    }
}