package com.example.workouttracker.user.controller;

import com.example.model.User;
import com.example.model.UserCreate;
import com.example.model.UserDetails;
import com.example.workouttracker.config.security.service.JwtService;
import com.example.workouttracker.core.exception.UserException;
import com.example.workouttracker.core.user.UserController;
import com.example.workouttracker.core.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UserControllerIT {

    @LocalServerPort
    private int port;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    private WebTestClient webTestClient;

    private String jwtToken;

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("password");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    public void setup() {
        webTestClient = WebTestClient.bindToController(new UserController(userService)).build();

        org.springframework.security.core.userdetails.UserDetails userDetails =
                new org.springframework.security.core.userdetails.User("testUser", "password", new ArrayList<>());
        jwtToken = jwtService.generateToken(userDetails);
    }

    @Test
    void getCurrentUser_ReturnsCurrentUser_StatusCode200() {

        User currentUser = new User();
        currentUser.setId("1");
        currentUser.setEmail("currentUser@example.com");
        currentUser.setFirstName("Current");
        currentUser.setLastName("User");

        when(userService.getCurrentUser()).thenReturn(currentUser);

        webTestClient.get()
                .uri("/user/me")
                .header("Authorization", "Bearer " + jwtToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class).isEqualTo(currentUser);
    }

    @Test
    void updateCurrentUser_ReturnsUpdatedUser_StatusCode200() {

        UserCreate userCreate = new UserCreate();
        userCreate.setFirstName("Updated");
        userCreate.setEmail("updated@example.com");

        User updatedUser = new User();
        updatedUser.setId("1");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("User");

        when(userService.updateCurrentUser(eq(userCreate))).thenReturn(updatedUser);

        webTestClient.put()
                .uri("/user/me")
                .header("Authorization", "Bearer " + jwtToken)
                .bodyValue(userCreate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class).isEqualTo(updatedUser);
    }

    @Test
    void updateUser_ReturnsUpdatedUserDetails_StatusCode200() {
        String userId = "123";

        UserCreate userCreate = new UserCreate();
        userCreate.setFirstName("Updated");
        userCreate.setEmail("updated@example.com");

        UserDetails updatedUserDetails = new UserDetails();
        updatedUserDetails.setId(userId);
        updatedUserDetails.setEmail("updated@example.com");
        updatedUserDetails.setFirstName("Updated");
        updatedUserDetails.setLastName("User");

        when(userService.updateUser(eq(userId), eq(userCreate))).thenReturn(updatedUserDetails);

        webTestClient.put()
                .uri("/user/{userId}", userId)
                .header("Authorization", "Bearer " + jwtToken)
                .bodyValue(userCreate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDetails.class).isEqualTo(updatedUserDetails);
    }

    @Test
    void updateUser_ThrowsUserNotFoundException_StatusCode404() {
        String userId = "123";

        UserCreate userCreate = new UserCreate();
        userCreate.setFirstName("Updated");
        userCreate.setEmail("updated@example.com");

        doThrow(new UserException(UserException.FailReason.NOT_FOUND))
                .when(userService).updateUser(eq(userId), eq(userCreate));
    }

    @Test
    void deleteUser_ReturnsStatusCode200() {
        String userId = "123";

        doNothing().when(userService).deleteUser(eq(userId));

        webTestClient.delete()
                .uri("/user/{userId}", userId)
                .header("Authorization", "Bearer " + jwtToken)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deleteUser_ThrowsUserNotFoundException_StatusCode404() {
        String userId = "123";

        doThrow(new UserException(UserException.FailReason.NOT_FOUND))
                .when(userService).deleteUser(eq(userId));

        webTestClient.delete()
                .uri("/user/{userId}", userId)
                .header("Authorization", "Bearer " + jwtToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo("USER_NOT_FOUND")
                .jsonPath("$.message").isEqualTo("User not found");
    }
}
