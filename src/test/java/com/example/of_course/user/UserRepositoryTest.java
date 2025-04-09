package com.example.of_course.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenSaveUser_thenCanBeFoundInDbById() {
        // Arrange
        User user = new User("persist@example.com", "hashedPass", "Persisted User");

        // Act
//        User savedUser = entityManager.persistFlushFind(user); // Persist, flush, and find
        User savedUser = userRepository.save(user);

        // Assert
        assertNotNull(savedUser, "User should exist in db");
        assertEquals(user.getEmail(), savedUser.getEmail(), "User email in db should match email in entity to save");
        assertEquals(user.getPassword(), savedUser.getPassword(), "User hashed password in db should match hashed password in entity to save"); // Check if hash is stored
    }

    @Test
    void whenExistsByEmail_givenExistingEmail_returnsOptionalUser() {
        // Arrange
        User user = new User("check@example.com", "hashedPass", "Check User");
        entityManager.persistAndFlush(user);

        // Act
        Optional<User> wrappedExistingUser = userRepository.findByEmail("check@example.com");
        assertTrue(wrappedExistingUser.isPresent(), "Optional should have a user within it");

        User existingUser = wrappedExistingUser.orElse(null);
        assertNotNull(existingUser, "User should exist from Optional");
        assertEquals(user.getEmail(), existingUser.getEmail(), "User email from db should match that from entity");
    }

    @Test
    void whenExistsByEmail_givenNonExistingEmail_returnsEmptyOptional() {
        // No user is added

        // Act
        Optional<User> wrappedExistingUser = userRepository.findByEmail("check@example.com");
        assertFalse(wrappedExistingUser.isPresent(), "No user should have this email in db yet");
    }
}
