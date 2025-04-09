package com.example.of_course.user;

import com.example.of_course.config.PasswordPolicyConfig;
import com.example.of_course.exception.UserEmailAlreadyExistsException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordPolicyConfig passwordPolicyConfig;

    @InjectMocks
    private UserService userService;

    void setUpPasswordPolicy() {
        when(passwordPolicyConfig.getMinLength()).thenReturn(12);
        when(passwordPolicyConfig.getMaxLength()).thenReturn(255);
    }


    @Nested
    class RegisterUserTests {

        @Test
        void whenSignupWithExistingEmail_thenThrowsUserEmailAlreadyExists() {
            SignupRequest request = new SignupRequest("test1@gmail.com", "123456789012");

            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

            assertThrows(UserEmailAlreadyExistsException.class, () -> userService.registerUser(request));

            verify(userRepository).findByEmail(request.getEmail());
//        verify(passwordEncoder, never()).encode(anyString());
            verify(userRepository, never()).save(any(User.class));
        }

        @Test
        void whenSignupWithNameTooLong_thenThrowsIllegalArgument() {
            SignupRequest request = new SignupRequest("test1@gmail.com", "123456789012", "this is a name that is too long and therefore throws an error");

            when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

            Exception e = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(request), "Exception should be thrown for name too long");
            assertTrue(e.getMessage().toLowerCase().contains("name length"), "Exception message should be for name length");

            verify(userRepository, never()).save(any(User.class));
        }

        @Test
        void whenSignupWithEmailTooShort_thenThrowsIllegalArgument() {
            SignupRequest request = new SignupRequest("a", "123456789012");

            when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

            Exception e = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(request), "Exception should be thrown for email too short");
            assertTrue(e.getMessage().toLowerCase().contains("invalid email length"), "Exception message should be for email length");

            verify(userRepository, never()).save(any(User.class));
        }

        @Test
        void whenSignupWithPasswordTooShort_thenThrowsIllegalArgument() {
            setUpPasswordPolicy();
            SignupRequest request = new SignupRequest("user1@gmail.com", "12345678901");

            when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

            assertNotEquals(0, passwordPolicyConfig.getMinLength(), "Password policy not configured properly");
            Exception e = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(request), "Exception should be thrown for password too short");
            assertTrue(e.getMessage().toLowerCase().contains("password length"), "Exception message should be for password length");

            verify(userRepository, never()).save(any(User.class));
        }


        @Test
        void whenSignupWithNewEmail_thenSavesUser_andHashesPassword() {
            setUpPasswordPolicy();

            SignupRequest request = new SignupRequest("test1@gmail.com", "123456789012");
            String hashedPassword = "asdf";
//            User savedUser = new User(request.getEmail(), request.getPassword(), request.getName());

            when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
            when(passwordEncoder.encode(request.getPassword())).thenReturn(hashedPassword);
            //CHECK what exactly does this do?
            // checks call to repo? do I even need this if I have separate repo tests?
            when(userRepository.save(any(User.class))).thenAnswer(invocationOnMock -> {
                User userToSave = invocationOnMock.getArgument(0);
                assertEquals(hashedPassword, userToSave.getPassword());
                assertEquals(request.getEmail(), userToSave.getEmail());
//            assertEquals(request.getName(), userToSave.getName());

                return new User(userToSave.getEmail(), userToSave.getPassword(), userToSave.getName());
            });

            Boolean success = userService.registerUser(request);

            assertTrue(success);

            verify(userRepository).findByEmail(request.getEmail());
            verify(passwordEncoder).encode(request.getPassword());
            verify(userRepository).save(any(User.class));
        }
    }
}
