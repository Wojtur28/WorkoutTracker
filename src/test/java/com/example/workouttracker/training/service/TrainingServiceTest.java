package com.example.workouttracker.training.service;

import com.example.model.*;
import com.example.workouttracker.core.exception.TrainingException;
import com.example.workouttracker.core.exercise.ExerciseEntity;
import com.example.workouttracker.core.exercise.ExerciseRepository;
import com.example.workouttracker.core.exercise.set.ExerciseSetEntity;
import com.example.workouttracker.core.training.TrainingEntity;
import com.example.workouttracker.core.training.TrainingRepository;
import com.example.workouttracker.core.training.TrainingService;
import com.example.workouttracker.core.user.UserEntity;
import com.example.workouttracker.core.user.UserRepository;
import com.example.workouttracker.mapper.TrainingMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @Mock
    private Authentication authentication;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TrainingService trainingService;

    private TrainingEntity trainingEntity1;
    private TrainingEntity trainingEntity2;
    private Training trainingDto1;
    private Training trainingDto2;
    private TrainingDetails trainingDetailsDto1;
    private TrainingCreate trainingCreate;
    private UserEntity userEntity;
    private ExerciseEntity exerciseEntity;
    private ExerciseSetEntity exerciseSetEntity;
    private ExerciseUpdate exerciseUpdate;

    private final Integer page = 0;
    private final Integer size = 10;

    @BeforeEach
    public void setUp() {
        setUpTrainingEntities();
        setUpExerciseEntities();
        setUpDtoEntities();
    }

    private void setUpTrainingEntities() {
        trainingEntity1 = TrainingEntity.builder()
                .name("name1")
                .description("description1")
                .exercises(new ArrayList<>())
                .build();
        trainingEntity1.setId(UUID.randomUUID());

        trainingEntity2 = TrainingEntity.builder()
                .name("name2")
                .description("description2")
                .exercises(new ArrayList<>())
                .build();
        trainingEntity2.setId(UUID.randomUUID());
    }

    private void setUpExerciseEntities() {
        exerciseEntity = ExerciseEntity.builder()
                .name("Exercise 1")
                .description("Description 1")
                .sets(new ArrayList<>())
                .training(trainingEntity1)
                .build();
        exerciseEntity.setId(UUID.randomUUID());


        exerciseSetEntity = ExerciseSetEntity.builder()
                .reps(3)
                .weight(100)
                .exercise(exerciseEntity)
                .build();
        exerciseEntity.getSets().add(exerciseSetEntity);
        trainingEntity1.getExercises().add(exerciseEntity);

        exerciseUpdate = new ExerciseUpdate();
        exerciseUpdate.setId(String.valueOf(exerciseEntity.getId()));
        exerciseUpdate.setName(exerciseEntity.getName());
        exerciseUpdate.setSets(new ArrayList<>());
    }

    private void setUpDtoEntities() {
        trainingDto1 = new Training();
        trainingDto1.setId(String.valueOf(trainingEntity1.getId()));
        trainingDto1.setName(trainingEntity1.getName());
        trainingDto1.setDescription(trainingEntity1.getDescription());

        trainingDto2 = new Training();
        trainingDto2.setId(String.valueOf(trainingEntity2.getId()));
        trainingDto2.setName(trainingEntity2.getName());
        trainingDto2.setDescription(trainingEntity2.getDescription());

        trainingDetailsDto1 = new TrainingDetails();
        trainingDetailsDto1.setId(String.valueOf(trainingEntity1.getId()));
        trainingDetailsDto1.setName(trainingEntity1.getName());
        trainingDetailsDto1.setDescription(trainingEntity1.getDescription());

        trainingCreate = new TrainingCreate();
        trainingCreate.setName(trainingEntity1.getName());
        trainingCreate.setDescription(trainingEntity1.getDescription());
    }

    @Test
    public void shouldGetTrainingListAndReturnSuccess() {
        Page<TrainingEntity> pageResult = new PageImpl<>(List.of(trainingEntity1, trainingEntity2));

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test");

        when(trainingRepository.findByCreatedBy_Email(eq("test"), any(PageRequest.class)))
                .thenReturn(pageResult);
        when(trainingMapper.toDto(trainingEntity1)).thenReturn(trainingDto1);
        when(trainingMapper.toDto(trainingEntity2)).thenReturn(trainingDto2);

        List<Training> result = trainingService.getTrainingsPage(page, size);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(trainingDto1, result.get(0));
        assertEquals(trainingDto2, result.get(1));
    }

    @Test
    public void shouldGetTrainingByIdAndReturnSuccess() {
        when(trainingRepository.findById(trainingEntity1.getId())).thenReturn(Optional.of(trainingEntity1));
        when(trainingMapper.toDetailsDto(trainingEntity1)).thenReturn(trainingDetailsDto1);

        TrainingDetails result = trainingService.getTraining(String.valueOf(trainingEntity1.getId()));

        assertNotNull(result);
        assertEquals(trainingEntity1.getId(), UUID.fromString(result.getId()));
        assertEquals(trainingEntity1.getName(), result.getName());
        assertEquals(trainingEntity1.getDescription(), result.getDescription());
    }

    @Test
    public void shouldGetTrainingByIdAndThrowNotFound() {
        when(trainingRepository.findById(trainingEntity1.getId())).thenReturn(Optional.empty());

        TrainingException exception = assertThrows(TrainingException.class, () ->
                trainingService.getTraining(String.valueOf(trainingEntity1.getId()))
        );

        assertEquals(TrainingException.FailReason.NOT_FOUND, exception.getFailReason());
        assertEquals("Error occurred: NOT_FOUND", exception.getMessage());
    }

    @Test
    public void shouldCreateTrainingAndReturnSuccess() {
        userEntity = UserEntity.builder().email("test").build();

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test");

        when(userRepository.findByEmail(eq("test"))).thenReturn(Optional.of(userEntity));
        when(trainingMapper.toEntity(trainingCreate)).thenReturn(trainingEntity1);
        when(trainingRepository.save(trainingEntity1)).thenReturn(trainingEntity1);
        when(trainingMapper.toDto(trainingEntity1)).thenReturn(trainingDto1);

        Training newTraining = trainingService.createTraining(trainingCreate);

        assertNotNull(newTraining);
        assertEquals(trainingEntity1.getName(), newTraining.getName());
        assertEquals(trainingEntity1.getDescription(), newTraining.getDescription());
    }

    @Test
    public void shouldCreateTrainingAndReturnUserNotFound() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test");

        when(userRepository.findByEmail(eq("test"))).thenReturn(Optional.empty());

        TrainingException exception = assertThrows(TrainingException.class, () ->
                trainingService.createTraining(trainingCreate)
        );

        assertEquals(TrainingException.FailReason.USER_NOT_FOUND, exception.getFailReason());
        assertEquals("Error occurred: USER_NOT_FOUND", exception.getMessage());
    }

    @Test
    public void shouldUpdateTrainingAndReturnSuccess() {
        when(trainingRepository.save(trainingEntity1)).thenReturn(trainingEntity1);
        when(trainingRepository.findById(trainingEntity1.getId())).thenReturn(Optional.of(trainingEntity1));
        when(trainingMapper.toDetailsDto(trainingEntity1)).thenReturn(trainingDetailsDto1);

        TrainingDetails result = trainingService.updateTraining(trainingDto1.getId(), trainingCreate);

        assertNotNull(result);
        assertEquals(trainingDetailsDto1.getName(), trainingCreate.getName());
        assertEquals(trainingDetailsDto1.getDescription(), trainingCreate.getDescription());
    }

    @Test
    public void shouldUpdateTrainingAndReturnTrainingNotFound() {
        UUID randomUuid = UUID.randomUUID();

        when(trainingRepository.findById(randomUuid)).thenReturn(Optional.empty());

        TrainingException exception = assertThrows(TrainingException.class, () ->
                trainingService.updateTraining(String.valueOf(randomUuid), trainingCreate)
        );

        assertEquals(TrainingException.FailReason.NOT_FOUND, exception.getFailReason());
        assertEquals("Error occurred: NOT_FOUND", exception.getMessage());
    }

    @Test
    public void shouldPatchExercisesInTrainingAndReturnSuccess() {
        UUID exerciseId = UUID.randomUUID();
        exerciseEntity.setId(exerciseId);
        exerciseUpdate.setId(String.valueOf(exerciseId));
        exerciseUpdate.setName("Updated Exercise Name");

        when(trainingRepository.findById(trainingEntity1.getId())).thenReturn(Optional.of(trainingEntity1));
        when(exerciseRepository.save(exerciseEntity)).thenReturn(exerciseEntity);
        when(trainingRepository.save(trainingEntity1)).thenReturn(trainingEntity1);
        when(trainingMapper.toDetailsDto(trainingEntity1)).thenReturn(trainingDetailsDto1);

        TrainingDetails result = trainingService.patchExercisesInTraining(
                String.valueOf(trainingEntity1.getId()), List.of(exerciseUpdate));

        assertNotNull(result);
        assertEquals("Updated Exercise Name", trainingEntity1.getExercises().getFirst().getName());
        assertEquals(1, trainingEntity1.getExercises().size());
    }

    @Test
    public void shouldAddNewExerciseToTrainingAndReturnSuccess() {
        ExerciseUpdate newExerciseUpdate = new ExerciseUpdate();
        newExerciseUpdate.setId(null);
        newExerciseUpdate.setName("New Exercise");
        newExerciseUpdate.setSets(new ArrayList<>());

        ExerciseEntity newExerciseEntity = new ExerciseEntity();
        newExerciseEntity.setName("New Exercise");

        when(trainingRepository.findById(trainingEntity1.getId())).thenReturn(Optional.of(trainingEntity1));
        when(exerciseRepository.save(any(ExerciseEntity.class))).thenReturn(newExerciseEntity);
        when(trainingRepository.save(trainingEntity1)).thenReturn(trainingEntity1);
        when(trainingMapper.toDetailsDto(trainingEntity1)).thenReturn(trainingDetailsDto1);

        TrainingDetails trainingDetailsDto = trainingService.patchExercisesInTraining(
                String.valueOf(trainingEntity1.getId()), List.of(newExerciseUpdate));

        assertNotNull(trainingDetailsDto);
        assertEquals(2, trainingEntity1.getExercises().size());
        assertEquals("New Exercise", trainingEntity1.getExercises().get(1).getName());
    }

    @Test
    public void shouldThrowExerciseNotFoundWhenPatchingExercises() {
        UUID nonExistentExerciseId = UUID.randomUUID();
        exerciseUpdate.setId(String.valueOf(nonExistentExerciseId));
        exerciseUpdate.setName("Non-existent Exercise");

        when(trainingRepository.findById(trainingEntity1.getId())).thenReturn(Optional.of(trainingEntity1));

        TrainingException exception = assertThrows(TrainingException.class, () ->
                trainingService.patchExercisesInTraining(
                        String.valueOf(trainingEntity1.getId()), List.of(exerciseUpdate))
        );

        assertEquals(TrainingException.FailReason.EXERCISE_NOT_FOUND, exception.getFailReason());
        assertEquals("Error occurred: EXERCISE_NOT_FOUND", exception.getMessage());
    }


    @Test
    public void shouldThrowTrainingNotFoundWhenAddingNewExercise() {
        ExerciseUpdate newExerciseUpdate = new ExerciseUpdate();
        newExerciseUpdate.setId(null);
        newExerciseUpdate.setName("New Exercise");

        when(trainingRepository.findById(trainingEntity1.getId())).thenReturn(Optional.empty());  // Simulate training not found

        TrainingException exception = assertThrows(TrainingException.class, () ->
                trainingService.patchExercisesInTraining(
                        String.valueOf(trainingEntity1.getId()), List.of(newExerciseUpdate))
        );

        assertEquals(TrainingException.FailReason.NOT_FOUND, exception.getFailReason());
        assertEquals("Error occurred: NOT_FOUND", exception.getMessage());
    }


    @Test
    public void shouldDeleteExerciseFromTrainingSuccessfully() {
        UUID trainingId = UUID.randomUUID();
        UUID exerciseId = UUID.randomUUID();
        exerciseEntity.setId(exerciseId);
        trainingEntity1.getExercises().add(exerciseEntity);

        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(trainingEntity1));

        trainingService.deleteExerciseFromTraining(trainingId.toString(), exerciseId.toString());

        verify(exerciseRepository).delete(exerciseEntity);
        verify(trainingRepository).save(trainingEntity1);
    }

    @Test
    public void shouldThrowTrainingNotFoundWhenDeletingExercise() {
        UUID trainingId = UUID.randomUUID();
        UUID exerciseId = UUID.randomUUID();
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.empty());

        TrainingException exception = assertThrows(TrainingException.class, () ->
                trainingService.deleteExerciseFromTraining(trainingId.toString(), exerciseId.toString())
        );

        assertEquals(TrainingException.FailReason.NOT_FOUND, exception.getFailReason());
        assertEquals("Error occurred: NOT_FOUND", exception.getMessage());
        verify(exerciseRepository, never()).delete(any(ExerciseEntity.class));
    }

    @Test
    public void shouldThrowExerciseNotFoundWhenDeletingExercise() {
        UUID trainingId = UUID.randomUUID();
        UUID nonExistentExerciseId = UUID.randomUUID();
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(trainingEntity1));

        TrainingException exception = assertThrows(TrainingException.class, () ->
                trainingService.deleteExerciseFromTraining(trainingId.toString(), nonExistentExerciseId.toString())
        );

        assertEquals(TrainingException.FailReason.EXERCISE_NOT_FOUND, exception.getFailReason());
        assertEquals("Error occurred: EXERCISE_NOT_FOUND", exception.getMessage());
        verify(exerciseRepository, never()).delete(any(ExerciseEntity.class));
    }

}