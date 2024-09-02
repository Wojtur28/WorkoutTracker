package com.example.workouttracker.training.service;

import com.example.model.Training;
import com.example.model.TrainingDetails;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
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

    private TrainingEntity trainingEnity1;
    private TrainingEntity trainingEnity2;
    private Training trainingDto1;
    private Training trainingDto2;
    private TrainingDetails trainingDetailsDto1;
    private TrainingDetails trainingDetailsDto2;
    private final Integer page = 0;
    private final Integer size = 10;

    @BeforeEach
    public void setUp(){
        trainingEnity1 = TrainingEntity.builder()
                .name("name1")
                .description("description1")
                .build();
        trainingEnity2 = TrainingEntity.builder()
                .name("name2")
                .description("description2")
                .build();

        trainingEnity1.setId(UUID.randomUUID());
        trainingEnity2.setId(UUID.randomUUID());

        trainingDto1 = new Training();
        trainingDto1.setId(String.valueOf(trainingEnity1.getId()));
        trainingDto1.setName(trainingEnity1.getName());
        trainingDto1.setDescription(trainingEnity1.getDescription());

        trainingDto2 = new Training();
        trainingDto2.setId(String.valueOf(trainingEnity2.getId()));
        trainingDto2.setName(trainingEnity2.getName());
        trainingDto2.setDescription(trainingEnity2.getDescription());

        trainingDetailsDto1 = new TrainingDetails();
        trainingDetailsDto1.setId(String.valueOf(trainingEnity1.getId()));
        trainingDetailsDto1.setName(trainingEnity1.getName());
        trainingDetailsDto1.setDescription(trainingEnity1.getDescription());
    }

    @Test
    public void shouldGetTrainingListAndReturnSuccess() {
        Page<TrainingEntity> pageResult = new PageImpl<>(List.of(trainingEnity1,trainingEnity2));

        when(trainingRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdOn"))))
                .thenReturn(pageResult);
        when(trainingMapper.toDto(trainingEnity1)).thenReturn(trainingDto1);
        when(trainingMapper.toDto(trainingEnity2)).thenReturn(trainingDto2);

        List<Training> result = trainingService.getTrainings(page, size);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(trainingDto1, result.get(0));
        assertEquals(trainingDto2, result.get(1));
    }

    @Test
    public void shouldGetTrainingByIdAndReturnSuccess() {
        when(trainingRepository.findById(trainingEnity1.getId())).thenReturn(Optional.of(trainingEnity1));
        when(trainingMapper.toDetailsDto(trainingEnity1)).thenReturn(trainingDetailsDto1);

        TrainingDetails result = trainingService.getTraining(String.valueOf(trainingEnity1.getId()));

        assertNotNull(result);
        assertEquals(trainingEnity1.getId(), UUID.fromString(result.getId()));
        assertEquals(trainingEnity1.getName(), result.getName());
        assertEquals(trainingEnity1.getDescription(), result.getDescription());

    }

}
