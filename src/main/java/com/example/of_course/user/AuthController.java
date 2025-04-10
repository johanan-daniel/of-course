package com.example.of_course.user;

import com.example.of_course.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Value("${jwt.expiration.ms}")
    private long jwtExpiration;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> createUser(@RequestBody SignupRequest request) {
        userService.registerUser(request);

        SignupResponse response =
                new SignupResponse(
                        HttpStatus.CREATED.value(),
                        "User registered successfully",
                        request.getEmail()
                );

        System.out.println("Registered user " + request.getEmail());
        return ResponseEntity.created(URI.create("moo")).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request) {
       String token = userService.loginUser(request);
//       ResponseMessage response = new ResponseMessage(message);
       LoginResponse response =
               new LoginResponse(
                       token,
                       jwtExpiration
               );

       return ResponseEntity.ok(response);
    }
}
