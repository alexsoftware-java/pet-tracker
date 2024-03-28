package com.some.pettracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {
    private long id;

    @NotNull
    private PetType petType;

    @NotNull
    private TrackerType trackerType;

    @NotNull(message = "Pet should have an owner :(")
    private Integer ownerId;

    private Boolean inZone;

    private Boolean lostTracker;
}