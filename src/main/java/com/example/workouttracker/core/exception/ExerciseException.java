package com.example.workouttracker.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class ExerciseException extends RuntimeException {

    public enum FailReason {
        NOT_FOUND,
        TRAINING_NOT_FOUND,
    }

    ExerciseException.FailReason failReason;
}
