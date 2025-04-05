package com.example.of_course.user;

import com.example.of_course.dto.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> createUser(@RequestBody SignupRequest request) {
        String message = userService.registerUser(request);
        ResponseMessage response = new ResponseMessage(message);

        System.out.println("Registered user " + request.getEmail());
        return ResponseEntity.ok(response);
    }
}
