package com.example.workouttracker.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import io.jsonwebtoken.ExpiredJwtException;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    public void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleBadCredentialsException() {
        BadCredentialsException exception = new BadCredentialsException("Bad credentials");
        ProblemDetail result = exceptionHandler.handleSecurityException(exception);

        assertThat(result.getStatus()).isEqualTo(401);
        assertThat(result.getDetail()).isEqualTo("Bad credentials");
        assertThat(result.getProperties()).containsEntry("description", "The username or password is incorrect");
        assertThat(result.getProperties()).containsEntry("errorCode", "BAD_CREDENTIALS");
    }

    @Test
    public void testHandleAccessDeniedException() {
        AccessDeniedException exception = new AccessDeniedException("Access denied");
        ProblemDetail result = exceptionHandler.handleSecurityException(exception);

        assertThat(result.getStatus()).isEqualTo(403);
        assertThat(result.getDetail()).isEqualTo("Access denied");
        assertThat(result.getProperties()).containsEntry("description", "You are not authorized to access this resource");
        assertThat(result.getProperties()).containsEntry("errorCode", "ACCESS_DENIED");
    }

    @Test
    public void testHandleExpiredJwtException() {
        ExpiredJwtException exception = mock(ExpiredJwtException.class);
        ProblemDetail result = exceptionHandler.handleSecurityException(exception);

        assertThat(result.getStatus()).isEqualTo(403);
        assertThat(result.getDetail()).isEqualTo(exception.getMessage());
        assertThat(result.getProperties()).containsEntry("description", "The JWT token has expired");
        assertThat(result.getProperties()).containsEntry("errorCode", "TOKEN_EXPIRED");
    }

    @Test
    public void testHandleUnknownException() {
        Exception exception = new Exception("Unknown error");
        ProblemDetail result = exceptionHandler.handleSecurityException(exception);

        assertThat(result.getStatus()).isEqualTo(500);
        assertThat(result.getDetail()).isEqualTo("Unknown error");
        assertThat(result.getProperties()).containsEntry("description", "Unknown internal server error.");
        assertThat(result.getProperties()).containsEntry("errorCode", "INTERNAL_SERVER_ERROR");
    }
}
