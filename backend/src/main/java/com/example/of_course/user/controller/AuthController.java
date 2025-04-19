package com.example.of_course.user.controller;

import com.example.of_course.common.dto.ResponseMessageDto;
import com.example.of_course.user.*;
import com.example.of_course.user.dto.LoginRequestDto;
import com.example.of_course.user.dto.LoginResponseDto;
import com.example.of_course.user.dto.SignupRequestDto;
import com.example.of_course.user.dto.SignupResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Value("${jwt.expiration.ms}")
    private long jwtExpiration;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessageDto> createUser(@RequestBody SignupRequestDto request) {
        userService.registerUser(request);

        SignupResponseDto response =
                new SignupResponseDto(
                        HttpStatus.CREATED.value(),
                        "User registered successfully",
                        request.getEmail()
                );

        System.out.println("Registered user " + request.getEmail());
        return ResponseEntity.created(URI.create("moo")).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto request) {
       String token = userService.loginUser(request);
//       ResponseMessageDto response = new ResponseMessageDto(message);
       LoginResponseDto response =
               new LoginResponseDto(
                       token,
                       jwtExpiration
               );

       return ResponseEntity.ok(response);
    }
}
