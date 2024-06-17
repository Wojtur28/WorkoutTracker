package com.example.workouttracker.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class UserException extends RuntimeException {

    public enum FailReason { NOT_FOUND, DOES_NOT_HAVE_ROLE, DOES_NOT_HAVE_PROPER_ROLE, ROLE_NOT_FOUND, NOT_UNIQUE_EMAIL }

    UserException.FailReason failReason;
}
