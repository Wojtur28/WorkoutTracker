package com.example.workouttracker.core.userMeasurement;

import com.example.workouttracker.AuditBase;
import com.example.workouttracker.core.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity(name = "user_measurements")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserMeasurementEntity extends AuditBase {

    @NotBlank(message = "The weight can't be blank")
    private double weight;
    @NotBlank(message = "The arms can't be blank")
    private double arms;
    @NotBlank(message = "The chest can't be blank")
    private double chest;
    @NotBlank(message = "The belly can't be blank")
    private double belly;
    @NotBlank(message = "The legs can't be blank")
    private double legs;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
