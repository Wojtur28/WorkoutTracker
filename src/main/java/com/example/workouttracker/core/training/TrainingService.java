package com.example.workouttracker.core.training;

import com.example.workouttracker.core.exception.TrainingException;
import com.example.workouttracker.core.user.UserRepository;
import com.example.workouttracker.mapper.TrainingMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Training;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.openapitools.model.TrainingDetails;
import org.openapitools.model.TrainingCreate;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class TrainingService {

    private final TrainingRepository trainingRepository;

    private final UserRepository userRepository;

    private final TrainingMapper trainingMapper;

    public List<Training> getTrainings(Integer page, Integer size) {
        return trainingRepository.findAll(PageRequest.of(page, size))
                .map(trainingMapper::toDto).getContent();
    }

    public TrainingDetails getTraining(String trainingId) {
        return trainingRepository.findById(UUID.fromString(trainingId))
                .map(trainingMapper::toDetailsDto)
                .orElseThrow(() -> new TrainingException(TrainingException.FailReason.NOT_FOUND));
    }

    public Training createTraining(TrainingCreate trainingCreate) {
        TrainingEntity newTrainingEntity = trainingMapper.toEntity(trainingCreate);
        newTrainingEntity.setUser(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new TrainingException(TrainingException.FailReason.USER_NOT_FOUND)));

        trainingRepository.save(newTrainingEntity);

        return trainingMapper.toDto(newTrainingEntity);
    }

    public TrainingDetails updateTraining(String trainingId, TrainingCreate trainingCreate) {
        TrainingEntity trainingEntity = trainingRepository.findById(UUID.fromString(trainingId))
                .orElseThrow(() -> new TrainingException(TrainingException.FailReason.NOT_FOUND));

        trainingEntity.setName(trainingCreate.getName());
        trainingEntity.setDescription(trainingCreate.getDescription());

        trainingRepository.save(trainingEntity);

        return trainingMapper.toDetailsDto(trainingEntity);
    }

    public void deleteTraining(String trainingId) {
        TrainingEntity training = trainingRepository.findById(UUID.fromString(trainingId))
                .orElseThrow(() -> new TrainingException(TrainingException.FailReason.NOT_FOUND));
        trainingRepository.delete(training);
    }
}
