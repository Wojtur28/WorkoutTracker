package com.example.workouttracker.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserException extends RuntimeException {

    public enum FailReason {
        NOT_FOUND,
        DOES_NOT_HAVE_ROLE,
        DOES_NOT_HAVE_PROPER_ROLE,
        ROLE_NOT_FOUND,
        NOT_UNIQUE_EMAIL }

    UserException.FailReason failReason;

    public UserException(FailReason failReason) {
        super("Error occurred: " + failReason);
        this.failReason = failReason;
    }
}
