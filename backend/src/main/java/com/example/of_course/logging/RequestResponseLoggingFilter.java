package com.example.of_course.logging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(req);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(res);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            logRequest(wrappedRequest);
            logResponse(wrappedResponse);
            wrappedResponse.copyBodyToResponse();
            System.out.println();
        }
    }

    private void logRequest(ContentCachingRequestWrapper req) {
        String httpMethod = req.getMethod();
        String uri = req.getRequestURI();

        logger.info("Request: {} {}", httpMethod, uri);
    }

    private void logResponse(ContentCachingResponseWrapper res) {
        int status = res.getStatus();
        byte[] responseArray = res.getContentAsByteArray();
        String responseBody = new String(responseArray, StandardCharsets.UTF_8);

        try {
            JsonNode json = objectMapper.readTree(responseBody);
            String message = json.has("message") ? json.get("message").asText() : "";

            logger.error("Response: {} {}", status, message);
            if (status >= 500) {
                String details = json.has("details") ? json.get("details").asText() : "No details";
                logger.error("Details: {}", details);
            }
        } catch (Exception e) {
            logger.error("Failed to parse message: status={} rawBody='{}'", status, responseBody);
        }
    }
}