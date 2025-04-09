package com.example.of_course.user;

import com.example.of_course.config.PasswordPolicyConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class SignupIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //CHECK do all of these need to be explicitly added?
    @Autowired
    private PasswordPolicyConfig passwordPolicyConfig;

    private static final String SIGNUP_URL = "/auth/signup";

    private ResultActions performSignup(SignupRequest request) throws Exception {
        String requestAsString = objectMapper.writeValueAsString(request);

        return mockMvc.perform(post(SIGNUP_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsString)
        );
    }


    @Test
    public void whenPostSignupIsValid_thenReturns201_andUserInDbWithHashedPassword() throws Exception {
        SignupRequest request = new SignupRequest("test1@example.com", "123456789012");

        performSignup(request)
                .andExpect(status().isCreated());

        User savedUser = userRepository.findByEmail(request.getEmail()).orElse(null);
        assertNotNull(savedUser, "User should be saved to db");
        assertEquals(request.getEmail(), savedUser.getEmail(), "Emails should match");
        // FIXME
//        assertEquals(request.getName(), savedUser.getName(), "Names should match");
        assertNotNull(savedUser.getPassword(), "Password should exist");
        assertNotEquals(request.getPassword(), savedUser.getPassword(), "Raw password should not match hashed password");
        assertTrue(passwordEncoder.matches(request.getPassword(), savedUser.getPassword()), "Hashed password should be correct");
    }

    @Test
    public void whenPostSignupWithShortPassword_thenReturns400_andNoUserInDb() throws Exception {
        SignupRequest request = new SignupRequest("test1@example.com", "a");
        long initialCount = userRepository.count();

        performSignup(request)
                .andExpect(status().isBadRequest());

        User savedUser = userRepository.findByEmail(request.getEmail()).orElse(null);

        assertNull(savedUser, "User with short password should not have been saved");
        assertEquals(initialCount, userRepository.count(), "User count should not increase (or change at all)");
    }
}
