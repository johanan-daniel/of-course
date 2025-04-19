package com.example.of_course.security;

import com.example.of_course.logging.RequestResponseLoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter authenticationFilter;

    private final RequestResponseLoggingFilter loggingFilter;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter authenticationFilter, RequestResponseLoggingFilter loggingFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.authenticationFilter = authenticationFilter;
        this.loggingFilter = loggingFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // disable for stateless APIs
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/**").permitAll() // Doesn't require JWT for these
                    .requestMatchers("/health/**").permitAll() // Doesn't require JWT for these
                    .anyRequest().authenticated() // All other endpoints are secured
            ) .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless sessions when using JWT
            ).addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
            .addFilterBefore(loggingFilter, JwtAuthenticationFilter.class);
        return http.build();
    }
}
