package com.example.workouttracker;

import com.example.workouttracker.user.User;
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
    private UUID id;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdby")
    private User createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifiedby")
    private User modifiedBy;

    @Column(name = "modifiedby", insertable = false, updatable = false)
    private UUID modifiedById;

    @CreatedDate
    @NotNull
    @Column(name = "createdon")
    private LocalDateTime createdOn = LocalDateTime.now();

    @LastModifiedDate
    @NotNull
    @Column(name = "modifiedon")
    private LocalDateTime modifiedOn = LocalDateTime.now();
}
