package com.example.workouttracker.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExerciseException extends RuntimeException {

    public enum FailReason {
        NOT_FOUND,
        TRAINING_NOT_FOUND,
    }

    TrainingException.FailReason failReason;

    public ExerciseException(TrainingException.FailReason failReason) {
        super("Error occurred: " + failReason);
        this.failReason = failReason;
    }
}
