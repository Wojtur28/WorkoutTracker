package com.example.workouttracker.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class UserMeasurementException extends RuntimeException {

    public enum FailReason { NOT_FOUND, INVALID_DATE, USER_NOT_FOUND }

    UserMeasurementException.FailReason failReason;
}
