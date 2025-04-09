package com.example.of_course.user;

import com.example.of_course.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> createUser(@RequestBody SignupRequest request) {
        userService.registerUser(request);

        SignupResponseMessage response =
                new SignupResponseMessage(
                        HttpStatus.CREATED.value(),
                        "User registered successfully",
                        request.getEmail()
                );

        System.out.println("Registered user " + request.getEmail());
        return ResponseEntity.created(URI.create("moo")).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> loginUser(@RequestBody LoginRequest request) {
       String message = userService.loginUser(request);
       ResponseMessage response = new ResponseMessage(message);

       return ResponseEntity.ok(response);
    }
}
