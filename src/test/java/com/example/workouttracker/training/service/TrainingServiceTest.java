package com.example.workouttracker.training.service;

import com.example.workouttracker.core.training.TrainingEntity;
import com.example.workouttracker.core.training.TrainingRepository;
import com.example.workouttracker.core.training.TrainingService;
import com.example.workouttracker.mapper.TrainingMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.Training;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingService trainingService;

    private TrainingEntity training1;
    private TrainingEntity training2;
    private Training trainingDto1;
    private Training trainingDto2;

    @BeforeEach
    public void setUp(){
        training1 = TrainingEntity.builder()
                .name("name1")
                .description("description1")
                .build();
        training2 = TrainingEntity.builder()
                .name("name2")
                .description("description2")
                .build();

        training1.setId(UUID.randomUUID());
        training2.setId(UUID.randomUUID());

        trainingDto1 = new Training();
        trainingDto1.setName("name1");
        trainingDto1.setDescription("description1");

        trainingDto2 = new Training();
        trainingDto2.setName("name2");
        trainingDto2.setDescription("description2");
    }

    @Test
    public void shouldReturnTraniningList(){
        List<TrainingEntity> trainingEntities = Arrays.asList(training1, training2);
        List<Training> trainingDtos = Arrays.asList(trainingDto1, trainingDto2);

        when(trainingRepository.findAll()).thenReturn(trainingEntities);
        when(trainingMapper.toDto(trainingEntities)).thenReturn(trainingDtos);

        ResponseEntity<List<Training>> response = trainingService.getTrainings();

        assertNotNull(response);
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        assertEquals(trainingDto1, response.getBody().get(0));
        assertEquals(trainingDto2, response.getBody().get(1));
    }

    @Test
    public void shouldReturnTrainingById(){
        when(trainingRepository.findById(training1.getId())).thenReturn(java.util.Optional.of(training1));
        when(trainingMapper.toDto(training1)).thenReturn(trainingDto1);

        ResponseEntity<Training> response = trainingService.getTraining(training1.getId().toString());

        assertNotNull(response);
        assertEquals(trainingDto1, response.getBody());
    }

    @Test
    public void shouldReturnNotFoundWhenTrainingNotFound() {
        when(trainingRepository.findById(training1.getId())).thenReturn(java.util.Optional.empty());

        ResponseEntity<Training> response = trainingService.getTraining(training1.getId().toString());

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
    }

    @Test
    public void shouldReturnTrainingWhenSaved(){
        when(trainingMapper.toEntity(trainingDto1)).thenReturn(training1);
        when(trainingRepository.save(training1)).thenReturn(training1);
        when(trainingMapper.toDto(training1)).thenReturn(trainingDto1);

        ResponseEntity<Training> response = trainingService.createTraining(trainingDto1);

        assertNotNull(response);
        assertEquals(trainingDto1, response.getBody());
    }

    @Test
    public void shouldUpdateTraining() {
        when(trainingRepository.findById(training1.getId())).thenReturn(java.util.Optional.of(training1));
        when(trainingRepository.save(training1)).thenReturn(training1);
        when(trainingMapper.toDto(training1)).thenReturn(trainingDto1);

        ResponseEntity<Training> response = trainingService.updateTraining(training1.getId().toString(), trainingDto1);

        assertNotNull(response);
        assertEquals(trainingDto1, response.getBody());
    }

    @Test
    public void shouldReturnNotFoundWhenTrainingNotUpdated() {
        when(trainingRepository.findById(training1.getId())).thenReturn(java.util.Optional.empty());

        ResponseEntity<Training> response = trainingService.updateTraining(training1.getId().toString(), trainingDto1);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
    }

    @Test
    public void shouldDeleteTraining() {
        when(trainingRepository.findById(training1.getId())).thenReturn(java.util.Optional.of(training1));

        ResponseEntity<Void> response = trainingService.deleteTraining(training1.getId().toString());

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    public void shouldReturnNotFoundWhenTrainingNotDeleted() {
        when(trainingRepository.findById(training1.getId())).thenReturn(java.util.Optional.empty());

        ResponseEntity<Void> response = trainingService.deleteTraining(training1.getId().toString());

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
    }
}
