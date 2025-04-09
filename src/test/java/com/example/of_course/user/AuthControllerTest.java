package com.example.of_course.user;

import com.example.of_course.exception.UserEmailAlreadyExistsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    private static final String LOGIN_URL = "/auth/login";

    // CHECK mock PasswordEncoder?
    //  maybe to check if it gets called for code coverage?? (this is checked in service tests)

    @Nested
    class SignupTests {
        private static final String SIGNUP_URL = "/auth/signup";

        private ResultActions performSignup(SignupRequest request) throws Exception {
            String requestAsString = objectMapper.writeValueAsString(request);

            return mockMvc.perform(post(SIGNUP_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestAsString)
            );
        }

        @Test
        void whenPostValidSignupRequest_thenReturns201Created() throws Exception {
            SignupRequest request = new SignupRequest();

            // mocks service successfully creating user with message returned
            when(userService.registerUser(any(SignupRequest.class))).thenReturn(true);

            // tests controller response when service successfully creates user
            performSignup(request)
                    .andExpect(status().isCreated());

            // verifies userService was called (tests path coverage?)
            verify(userService).registerUser(any(SignupRequest.class));
        }

        @Test
        void whenPostSignupWithExistingEmail_thenReturns409Conflict() throws Exception {
            SignupRequest request = new SignupRequest();

            // mocks service throwing exception for existing email
            when(userService.registerUser(any(SignupRequest.class)))
                    .thenThrow(new UserEmailAlreadyExistsException("Email address is already in use.", "user1@gmail.com"));

            // tests controller response when service throws exception
            performSignup(request)
                    .andExpect(status().isConflict());
//                .andExpect(jsonPath("$.email").value("user1@gmail.com"))
//                .andExpect(jsonPath("$.message").value("Email already in use"))
//                .andExpect(jsonPath("$.details").value("Email address is already in use."));

//        verify(userService).registerUser(any(SignupRequest.class));
        }
    }
}
