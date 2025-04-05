package com.example.of_course;

import com.example.of_course.dto.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/health")
    public ResponseEntity<ResponseMessage> healthCheck() {
        ResponseMessage responseMessage = new ResponseMessage("OK");
        return ResponseEntity.ok(responseMessage);
    }
}
