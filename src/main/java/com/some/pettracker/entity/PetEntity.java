package com.some.pettracker.entity;


import com.some.pettracker.dto.PetType;
import com.some.pettracker.dto.TrackerType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Use SINGLE_TABLE strategy for inheritance between cats and other pets
@DiscriminatorColumn(name = "pet_type", discriminatorType = DiscriminatorType.STRING)
@Data
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "pet_type", insertable=false, updatable=false)
    private PetType petType;
    @Enumerated(EnumType.STRING)
    private TrackerType trackerType;
    @Min(value = 0)
    private int ownerId;
    private Boolean inZone;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}