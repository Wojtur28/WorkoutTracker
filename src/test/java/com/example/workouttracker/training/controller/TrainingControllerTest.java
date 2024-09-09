package com.example.workouttracker.training.controller;

import com.example.model.Training;
import com.example.model.TrainingCreate;
import com.example.model.TrainingDetails;
import com.example.workouttracker.config.security.service.JwtService;
import com.example.workouttracker.core.exception.TrainingException;
import com.example.workouttracker.core.training.TrainingController;
import com.example.workouttracker.core.training.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class TrainingControllerTest {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @MockBean
    private TrainingService trainingService;

    @Autowired
    private JwtService jwtService;

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
        webTestClient = WebTestClient.bindToController(new TrainingController(trainingService)).build();

        UserDetails userDetails = new User("testUser", "password", new ArrayList<>());
        jwtToken = jwtService.generateToken(userDetails);

    }

    @Test
    void getTrainings_ReturnsListOfTrainings_StatusCode200() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/training")
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .build())
                .header("Authorization", "Bearer " + jwtToken)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Training.class);
    }

    @Test
    void getTrainingById_ReturnsTrainingDetails_StatusCode200() {
        UUID trainingId = UUID.randomUUID();
        webTestClient.get()
                .uri("/training/{trainingId}", trainingId)
                .header("Authorization", "Bearer " + jwtToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TrainingDetails.class);
    }

    @Test
    void getTrainingById_ThrowsTrainingNotFoundException_StatusCode404() {
        String nonExistentTrainingId = "nonexistent-id";

        when(trainingService.getTraining(eq(nonExistentTrainingId)))
                .thenThrow(new TrainingException(TrainingException.FailReason.NOT_FOUND));

        webTestClient.get()
                .uri("/training/{trainingId}", nonExistentTrainingId)
                .header("Authorization", "Bearer " + jwtToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo("TRAINING_NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Training not found");
    }

    @Test
    void createTraining_ReturnsCreatedTraining_StatusCode200() {
        TrainingCreate trainingCreate = new TrainingCreate();
        trainingCreate.setName("New Training");

        webTestClient.post()
                .uri("/training")
                .header("Authorization", "Bearer " + jwtToken)
                .bodyValue(trainingCreate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Training.class);
    }

    @Test
    void updateTraining_ReturnsUpdatedTrainingDetails_StatusCode200() {
        String trainingId = "123";
        TrainingCreate updatedTraining = new TrainingCreate();
        updatedTraining.setName("Updated Training");

        webTestClient.put()
                .uri("/training/{trainingId}", trainingId)
                .header("Authorization", "Bearer " + jwtToken)
                .bodyValue(updatedTraining)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TrainingDetails.class);
    }

    @Test
    void updateTraining_ThrowsTrainingNotFoundException_StatusCode404() {
        String nonExistentTrainingId = "nonexistent-id";
        TrainingCreate updatedTraining = new TrainingCreate();
        updatedTraining.setName("Updated Training");

        when(trainingService.updateTraining(eq(nonExistentTrainingId), eq(updatedTraining)))
                .thenThrow(new TrainingException(TrainingException.FailReason.NOT_FOUND));

        webTestClient.put()
                .uri("/training/{trainingId}", nonExistentTrainingId)
                .header("Authorization", "Bearer " + jwtToken)
                .bodyValue(updatedTraining)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo("TRAINING_NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Training not found");
    }

    @Test
    void deleteTraining_ReturnsStatusCode200() {
        String trainingId = "123";

        webTestClient.delete()
                .uri("/training/{trainingId}", trainingId)
                .header("Authorization", "Bearer " + jwtToken)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deleteTraining_ThrowsTrainingNotFoundException_StatusCode404() {
        String nonExistentTrainingId = "123";

        doThrow(new TrainingException(TrainingException.FailReason.NOT_FOUND))
                .when(trainingService).deleteTraining(eq(nonExistentTrainingId));

        webTestClient.delete()
                .uri("/training/{trainingId}", nonExistentTrainingId)
                .header("Authorization", "Bearer " + jwtToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo("TRAINING_NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Training not found");
    }

    @Test
    void deleteExerciseFromTraining_ReturnsStatusCode200() {
        String trainingId = "123";
        String exerciseId = "456";

        webTestClient.delete()
                .uri("/training/{trainingId}/exercises/{exerciseId}", trainingId, exerciseId)
                .header("Authorization", "Bearer " + jwtToken)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deleteExerciseFromTraining_ThrowsTrainingNotFoundException_StatusCode404() {
        String nonExistentTrainingId = "123";
        String exerciseId = "456";

        doThrow(new TrainingException(TrainingException.FailReason.NOT_FOUND))
                .when(trainingService).deleteExerciseFromTraining(eq(nonExistentTrainingId), eq(exerciseId));

        webTestClient.delete()
                .uri("/training/{trainingId}/exercises/{exerciseId}", "123", exerciseId)
                .header("Authorization", "Bearer " + jwtToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo("TRAINING_NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Training not found");
    }
}
