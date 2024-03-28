package com.some.pettracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class PetTypeDto {
    private PetType petType;
    private List<TrackerTypeDto> trackerTypes;
}