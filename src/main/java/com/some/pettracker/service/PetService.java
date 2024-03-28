package com.some.pettracker.service;

import com.some.pettracker.converter.PetConverter;
import com.some.pettracker.dto.*;
import com.some.pettracker.entity.PetEntity;
import com.some.pettracker.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final PetConverter petConverter;

    public PetEntity savePet(PetDto petDto) {
        PetEntity petEntity = petConverter.convert(petDto);
        return petRepository.save(petEntity);
    }

    public List<PetDto> getAllPetsByOwnerId(int ownerId) {
        List<PetEntity> petEntities = petRepository.findByOwnerId(ownerId);
        return petEntities.stream()
                .map(petConverter::convert)
                .collect(Collectors.toList());
    }

    public List<PetTypeDto> getPetsOutsideZone() {
        List<PetEntity> petEntities = petRepository.findAllByInZoneFalse();
        Map<PetType, Map<TrackerType, List<PetEntity>>> groupedPets = petEntities.stream()
                .collect(Collectors.groupingBy(PetEntity::getPetType,
                        Collectors.groupingBy(PetEntity::getTrackerType)));

        return groupedPets.entrySet().stream()
                .map(entry -> new PetTypeDto(entry.getKey(),
                        entry.getValue().entrySet().stream()
                                .map(innerEntry -> new TrackerTypeDto(innerEntry.getKey(), innerEntry.getValue().stream()
                                        .map(petConverter::convert)
                                        .collect(Collectors.toList())))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}