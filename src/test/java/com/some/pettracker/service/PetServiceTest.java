package com.some.pettracker.service;

import com.some.pettracker.converter.PetConverter;
import com.some.pettracker.dto.PetType;
import com.some.pettracker.dto.PetTypeDto;
import com.some.pettracker.dto.TrackerType;
import com.some.pettracker.entity.PetEntity;
import com.some.pettracker.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
class PetServiceTest {

    @MockBean
    private PetRepository petRepository;

    @Test
    void testGetPetsOutsideZone() {
        // given
        PetService petService = new PetService(petRepository, new PetConverter());
        PetEntity petEntity1 = new PetEntity();
        petEntity1.setPetType(PetType.CAT);
        petEntity1.setTrackerType(TrackerType.SMALL_CAT);
        petEntity1.setOwnerId(1);
        petEntity1.setInZone(false); // Outside the zone
        when(petRepository.findAllByInZoneFalse()).thenReturn(List.of(petEntity1));

        // when
        List<PetTypeDto> result = petService.getPetsOutsideZone();

        // then
        assertEquals(1, result.size());
        assertEquals(PetType.CAT, result.get(0).getPetType());
        assertEquals(1, result.get(0).getTrackerTypes().size());
        assertEquals(TrackerType.SMALL_CAT, result.get(0).getTrackerTypes().get(0).getTrackerType());

        verify(petRepository, times(1)).findAllByInZoneFalse();
    }
}