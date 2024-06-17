/*
package com.example.workouttracker.exercise.service;

import com.example.workouttracker.core.exercise.ExerciseEntity;
import com.example.workouttracker.core.exercise.ExerciseRepository;
import com.example.workouttracker.core.exercise.ExerciseService;
import com.example.workouttracker.mapper.ExerciseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.Exercise;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ExerciseMapper exerciseMapper;

    @InjectMocks
    private ExerciseService exerciseService;

    private ExerciseEntity exercise1;
    private ExerciseEntity exercise2;
    private Exercise exerciseDto1;
    private Exercise exerciseDto2;

    @BeforeEach
    public void setUp(){
        exercise1 = ExerciseEntity.builder()
                .name("name1")
                .description("description1")
                .sets(3)
                .reps(10)
                .build();

        exercise2 = ExerciseEntity.builder()
                .name("name2")
                .description("description2")
                .sets(4)
                .reps(12)
                .build();

        exercise1.setId(UUID.randomUUID());
        exercise2.setId(UUID.randomUUID());

        exerciseDto1 = new Exercise();
        exerciseDto1.setName("name1");
        exerciseDto1.setDescription("description1");
        exerciseDto1.setSets(3);
        exerciseDto1.setReps(10);

        exerciseDto2 = new Exercise();
        exerciseDto2.setName("name2");
        exerciseDto2.setDescription("description2");
        exerciseDto2.setSets(4);
        exerciseDto2.setReps(12);

    }

*/
/*    @Test
    @Disabled
    public void shouldReturnExerciseList(){
        when(exerciseRepository.findAll()).thenReturn(List.of(exercise1, exercise2));
        when(exerciseMapper.toDto(List.of(exercise1, exercise2))).thenReturn(List.of(exerciseDto1, exerciseDto2));

        ResponseEntity<List<Exercise>> exercises = exerciseService.getExercises();

        assertNotNull(exercises);
        assertEquals(2, Objects.requireNonNull(exercises.getBody()).size());
        assertEquals(exerciseDto1, exercises.getBody().get(0));
        assertEquals(exerciseDto2, exercises.getBody().get(1));
    }*//*


    @Test
    public void shouldReturnExerciseById(){
        when(exerciseRepository.findById(exercise1.getId())).thenReturn(java.util.Optional.of(exercise1));
        when(exerciseMapper.toDto(exercise1)).thenReturn(exerciseDto1);

        ResponseEntity<Exercise> exercise = exerciseService.getExercise(exercise1.getId().toString());

        assertNotNull(exercise);
        assertEquals(exerciseDto1, exercise.getBody());
    }

    @Test
    public void shouldReturnNotFoundWhenExerciseNotFound() {
        when(exerciseRepository.findById(exercise1.getId())).thenReturn(java.util.Optional.empty());

        ResponseEntity<Exercise> exercise = exerciseService.getExercise(exercise1.getId().toString());
    }

    @Test
    public void shouldReturnExerciseCreated(){
        when(exerciseMapper.toEntity(exerciseDto1)).thenReturn(exercise1);
        when(exerciseRepository.save(exercise1)).thenReturn(exercise1);
        when(exerciseMapper.toDto(exercise1)).thenReturn(exerciseDto1);

        ResponseEntity<Exercise> exercise = exerciseService.createExercise(exerciseDto1);

        assertNotNull(exercise);
        assertEquals(exerciseDto1, exercise.getBody());
    }

    @Test
    public void shouldReturnExerciseUpdated(){
        when(exerciseRepository.findById(exercise1.getId())).thenReturn(java.util.Optional.of(exercise1));
        when(exerciseRepository.save(exercise1)).thenReturn(exercise1);
        when(exerciseMapper.toDto(exercise1)).thenReturn(exerciseDto1);

        Exercise updatedExercise = new Exercise();

        updatedExercise.setName("newName");
        updatedExercise.setDescription("newDescription");
        updatedExercise.setSets(5);
        updatedExercise.setReps(15);

        ResponseEntity<Exercise> exercise = exerciseService.updateExercise(exercise1.getId().toString(), updatedExercise);

        assertNotNull(exercise);
        assertEquals(exerciseDto1, exercise.getBody());
    }

    @Test
    public void shouldReturnNotFoundWhenExerciseNotUpdated() {
        when(exerciseRepository.findById(exercise1.getId())).thenReturn(java.util.Optional.empty());

        Exercise updatedExercise = new Exercise();

        updatedExercise.setName("newName");
        updatedExercise.setDescription("newDescription");
        updatedExercise.setSets(5);
        updatedExercise.setReps(15);

        ResponseEntity<Exercise> exercise = exerciseService.updateExercise(exercise1.getId().toString(), updatedExercise);

    }

    @Test
    public void shouldReturnExerciseDeleted(){
        when(exerciseRepository.findById(exercise1.getId())).thenReturn(java.util.Optional.of(exercise1));

        ResponseEntity<Void> exercise = exerciseService.deleteExercise(exercise1.getId().toString());

        assertNotNull(exercise);
    }

    @Test
    public void shouldReturnNotFoundWhenExerciseNotDeleted() {
        when(exerciseRepository.findById(exercise1.getId())).thenReturn(java.util.Optional.empty());

        ResponseEntity<Void> exercise = exerciseService.deleteExercise(exercise1.getId().toString());
    }



}
*/
