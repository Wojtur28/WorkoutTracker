package com.example.workouttracker.authentication.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.workouttracker.core.exercise.ExerciseService;
import com.example.workouttracker.core.training.TrainingService;
import com.example.workouttracker.core.user.*;
import com.example.workouttracker.core.userMeasurement.UserMeasurementService;
import com.example.workouttracker.security.controller.AuthenticationController;
import com.example.workouttracker.security.dto.SignInUser;
import com.example.workouttracker.security.dto.SignUpUser;
import com.example.workouttracker.security.service.AuthenticationService;
import com.example.workouttracker.security.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;
import java.util.UUID;

@WebMvcTest(controllers = AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private ExerciseService exerciseService;

    @MockBean
    private TrainingService trainingService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMeasurementService userMeasurementService;

    private UserEntity testUser;

    @BeforeEach
    public void setUp() {
        // Set up a test user
        testUser = new UserEntity();
        testUser.setId(UUID.randomUUID());
        testUser.setEmail("test@example.com");
        testUser.setPassword("test");
        testUser.setRoles(Set.of(RoleType.ADMINISTRATOR));
        testUser.setIsTermsAndConditionsAccepted(true);

        // Mock behavior of authenticationService
        when(authenticationService.signup(any(SignUpUser.class))).thenReturn(testUser);
        when(authenticationService.authenticate(any(SignInUser.class))).thenReturn(testUser);

        // Mock behavior of jwtService
        when(jwtService.generateToken(any(UserEntity.class))).thenReturn("fake-jwt-token");
        when(jwtService.getExpirationTime()).thenReturn(3600L);
    }

    @Test
    @WithMockUser
    @Disabled
    public void shouldReturnTokenOnSignIn() throws Exception {
        SignInUser signInUser = new SignInUser();
        signInUser.setEmail("test@example.com");
        signInUser.setPassword("test");

        mockMvc.perform(post("/auth/signin")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(signInUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.expiresIn").value(3600));
    }

    @Test
    @WithMockUser
    @Disabled
    public void shouldSignUpUser() throws Exception {
        SignUpUser signUpUser = new SignUpUser();
        signUpUser.setEmail("test@example.com");
        signUpUser.setPassword("test");

        mockMvc.perform(post("/auth/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(signUpUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @WithMockUser
    @Disabled
    public void shouldThrowErrorWhenUserAlreadyExists() throws Exception {
        // Assuming your authenticationService.signup throws an exception when user already exists
        when(authenticationService.signup(any(SignUpUser.class)))
                .thenThrow(new RuntimeException("User with this email already exists"));

        SignUpUser signUpUser = new SignUpUser();
        signUpUser.setEmail("test@example.com");
        signUpUser.setPassword("test");

        mockMvc.perform(post("/auth/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(signUpUser)))
                .andExpect(status().isConflict());
    }
}
