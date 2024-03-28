package com.some.pettracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TrackerTypeDto {
    private TrackerType trackerType;
    private List<PetDto> pets;
}
