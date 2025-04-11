package com.example.of_course.common;

import com.example.of_course.common.dto.ResponseMessageDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/health")
    public ResponseEntity<ResponseMessageDto> healthCheck() {
        ResponseMessageDto responseMessage = new ResponseMessageDto("OK");
        return ResponseEntity.ok(responseMessage);
    }
}
