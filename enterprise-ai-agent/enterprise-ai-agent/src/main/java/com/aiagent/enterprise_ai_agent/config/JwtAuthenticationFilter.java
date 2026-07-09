package com.aiagent.enterprise_ai_agent.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
extends OncePerRequestFilter{

    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain)

            throws ServletException, IOException {

        String header =
                request.getHeader("Authorization");

        if(header==null ||
                !header.startsWith("Bearer ")){

            filterChain.doFilter(request,response);

            return;

        }

        String token =
                header.substring(7);

        if(jwtService.isTokenValid(token)){

            String email =
                    jwtService.extractUsername(token);

            UserDetails user =
                    userDetailsService
                    .loadUserByUsername(email);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(

                            user,

                            null,

                            user.getAuthorities()

                    );

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)

            );

            SecurityContextHolder
            .getContext()
            .setAuthentication(auth);

        }

        filterChain.doFilter(request,response);

    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        return path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/auth");
    }

}