package com.example.workouttracker;

import com.example.workouttracker.core.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode
public abstract class AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdby")
    @EqualsAndHashCode.Exclude
    private UserEntity createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifiedby")
    @EqualsAndHashCode.Exclude
    private UserEntity modifiedBy;

    @CreatedDate
    @NotNull
    @Column(name = "createdon")
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdOn = LocalDateTime.now();

    @LastModifiedDate
    @NotNull
    @Column(name = "modifiedon")
    @EqualsAndHashCode.Exclude
    private LocalDateTime modifiedOn = LocalDateTime.now();
}
