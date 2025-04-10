package com.example.of_course.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Override
    // CHECK is NonNull required?
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = parseToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }


        // CHECK is this needed?
        //  && SecurityContextHolder.getContext().getAuthentication() == null

        // CHECK
        //  shouldn't token be validated before checking userEmail?
        String userEmail = jwtService.extractUserEmail(token);
        if (userEmail != null && jwtService.validateToken(token)) {
            // if valid, set up authentication for email & add to security context to be accessed by endpoints
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userEmail, null, null);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // CHECK clear context if validateToken fails or userEmail is null?
        //  SecurityContextHolder.clearContext();

        filterChain.doFilter(request, response);
    }

    private String parseToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }
}