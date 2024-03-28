package com.some.pettracker.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CAT") // if PetEntity.petType == PetType.CAT then CatEntity will be used
@Data
public class CatEntity extends PetEntity {
    private Boolean lostTracker;
}

