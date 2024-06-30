package com.example.workouttracker.core.userMeasurement;

import com.example.workouttracker.core.exception.UserMeasurementException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.openapitools.model.UserMeasurement;
import org.openapitools.api.UserMeasurementApi;
import org.openapitools.model.ErrorResponse;
import org.openapitools.model.UserMeasurementCreate;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserMeasurementController implements UserMeasurementApi {

    private final UserMeasurementService userMeasurementService;

    @Override
    public ResponseEntity<List<UserMeasurement>> getUserMeasurements(@RequestParam Integer page,
                                                                     @RequestParam Integer size) {
        List<UserMeasurement> userMeasurements = userMeasurementService.getUserMeasurements(page, size);
        return ResponseEntity.ok(userMeasurements);
    }

    @Override
    public ResponseEntity<UserMeasurement> getUserMeasurementById(@PathVariable String userMeasurementId) {
        UserMeasurement userMeasurement = userMeasurementService.getUserMeasurement(userMeasurementId);
        return ResponseEntity.ok(userMeasurement);
    }

    @Override
    public ResponseEntity<UserMeasurement> createUserMeasurement(@RequestBody UserMeasurementCreate userMeasurementCreate) {
        UserMeasurement createdUserMeasurement = userMeasurementService.createUserMeasurement(userMeasurementCreate);
        return ResponseEntity.ok(createdUserMeasurement);
    }

    @Override
    public ResponseEntity<UserMeasurement> updateUserMeasurement(@PathVariable String userMeasurementId, @RequestBody UserMeasurementCreate userMeasurementCreate) {
        UserMeasurement updatedUserMeasurement = userMeasurementService.updateUserMeasurement(userMeasurementId, userMeasurementCreate);
        return ResponseEntity.ok(updatedUserMeasurement);
    }

    @Override
    public ResponseEntity<Void> deleteUserMeasurement(@PathVariable String userMeasurementId) {
        userMeasurementService.deleteUserMeasurement(userMeasurementId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UserMeasurementException.class)
    public ResponseEntity<String> handleException(UserMeasurementException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        switch (e.getFailReason()) {
            case NOT_FOUND:
                errorResponse.setCode("USER_MEASUREMENT_NOT_FOUND");
                errorResponse.setMessage("User measurement not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse.toString());
            case USER_NOT_FOUND:
                errorResponse.setCode("USER_NOT_FOUND");
                errorResponse.setMessage("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse.toString());
            default:
                errorResponse.setCode("INTERNAL_SERVER_ERROR");
                errorResponse.setMessage("Internal server error");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }
    }
}
