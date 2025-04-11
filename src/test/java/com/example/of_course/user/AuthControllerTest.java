package com.example.of_course.user;

import com.example.of_course.exception.UserEmailAlreadyExistsException;
import com.example.of_course.security.JwtService;
import com.example.of_course.user.controller.AuthController;
import com.example.of_course.user.dto.SignupRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Import(SecurityConfig.class)
// ignore filters to only test controller behavior
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    private static final String LOGIN_URL = "/api/auth/login";

    // CHECK mock PasswordEncoder?
    //  maybe to check if it gets called for code coverage?? (this is checked in service tests)

    @Nested
    class SignupTests {
        private static final String SIGNUP_URL = "/api/auth/signup";

        private ResultActions performSignup(SignupRequestDto request) throws Exception {
            String requestAsString = objectMapper.writeValueAsString(request);

            return mockMvc.perform(post(SIGNUP_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestAsString)
            );
        }

        @Test
        void whenPostValidSignupRequest_thenReturns201Created() throws Exception {
            SignupRequestDto request = new SignupRequestDto();

            // mocks service successfully creating user with message returned
            when(userService.registerUser(any(SignupRequestDto.class))).thenReturn(true);

            // tests controller response when service successfully creates user
            performSignup(request)
                    .andExpect(status().isCreated());

            // verifies userService was called (tests path coverage?)
            verify(userService).registerUser(any(SignupRequestDto.class));
        }

        @Test
        void whenPostSignupWithExistingEmail_thenReturns409Conflict() throws Exception {
            SignupRequestDto request = new SignupRequestDto();

            // mocks service throwing exception for existing email
            when(userService.registerUser(any(SignupRequestDto.class)))
                    .thenThrow(new UserEmailAlreadyExistsException("Email address is already in use.", "user1@gmail.com"));

            // tests controller response when service throws exception
            performSignup(request)
                    .andExpect(status().isConflict());
//                .andExpect(jsonPath("$.email").value("user1@gmail.com"))
//                .andExpect(jsonPath("$.message").value("Email already in use"))
//                .andExpect(jsonPath("$.details").value("Email address is already in use."));

//        verify(userService).registerUser(any(SignupRequestDto.class));
        }
    }
}
