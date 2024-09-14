package com.example.workouttracker.endToEnd;


import com.example.workouttracker.config.security.service.JwtService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Transactional
public class EndToEndTest {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @Autowired
    JwtService jwtService;

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
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();

    }

    @Test
    @Disabled
    public void shouldCreateNewUserAndLogin() {
        String signUpRequest = """
        {
            "email": "testuser@example.com",
            "firstName": "Test",
            "lastName": "User",
            "password": "password123",
            "isTermsAndConditionsAccepted": true,
            "genders": ["MALE"]
        }
    """;

        webTestClient.post()
                .uri("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(signUpRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").isEqualTo("testuser@example.com")
                .jsonPath("$.firstName").isEqualTo("Test")
                .jsonPath("$.lastName").isEqualTo("User");

        String signInRequest = """
        {
            "email": "testuser@example.com",
            "password": "password123"
        }
    """;

        String jwtToken = webTestClient.post()
                .uri("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(signInRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.token").toString();

    }

}
