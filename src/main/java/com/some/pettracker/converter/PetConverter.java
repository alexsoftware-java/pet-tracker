package com.some.pettracker.converter;

import com.some.pettracker.dto.PetDto;
import com.some.pettracker.dto.PetType;
import com.some.pettracker.entity.CatEntity;
import com.some.pettracker.entity.PetEntity;
import org.springframework.stereotype.Component;

@Component
public class PetConverter {

    public PetEntity convert(PetDto petDto) {
        PetEntity petEntity;
        if (petDto.getPetType() == PetType.CAT) {
            petEntity = new CatEntity();
            ((CatEntity) petEntity).setLostTracker(petDto.getLostTracker());
        } else {
            petEntity = new PetEntity();
        }
        petEntity.setPetType(petDto.getPetType());
        petEntity.setTrackerType(petDto.getTrackerType());
        petEntity.setOwnerId(petDto.getOwnerId());
        petEntity.setInZone(petDto.getInZone());
        return petEntity;
    }

    public PetDto convert(PetEntity petEntity) {
        PetDto petDto = new PetDto();
        petDto.setId(petEntity.getId());
        petDto.setPetType(petEntity.getPetType());
        petDto.setTrackerType(petEntity.getTrackerType());
        petDto.setOwnerId(petEntity.getOwnerId());
        petDto.setInZone(petEntity.getInZone());
        if (petEntity instanceof CatEntity) {
            petDto.setLostTracker(((CatEntity) petEntity).getLostTracker());
        }

        return petDto;
    }
}

