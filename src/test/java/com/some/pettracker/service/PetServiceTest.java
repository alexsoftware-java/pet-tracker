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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
class PetServiceTest {

    @MockBean
    private PetRepository petRepository;

    private PetService petService;

    @Test
    void testGetPetsOutsideZone() {
        // given
        petService = new PetService(petRepository, new PetConverter());
        PetEntity petEntity1 = new PetEntity();
        petEntity1.setPetType(PetType.CAT);
        petEntity1.setTrackerType(TrackerType.SMALL_CAT);
        petEntity1.setOwnerId(1);
        petEntity1.setInZone(false); // Outside the zone
        PetEntity petEntity2 = new PetEntity();
        petEntity2.setPetType(PetType.CAT);
        petEntity2.setTrackerType(TrackerType.BIG_CAT);
        petEntity2.setOwnerId(2);
        petEntity2.setInZone(true); // Inside the zone
        when(petRepository.findAllByInZoneFalse()).thenReturn(Arrays.asList(petEntity1, petEntity2));

        // when
        List<PetTypeDto> result = petService.getPetsOutsideZone();

        // then
        assertEquals(1, result.size());
        assertEquals(PetType.CAT, result.get(0).getPetType());
        assertEquals(2, result.get(0).getTrackerTypes().size());
        assertEquals(TrackerType.SMALL_CAT, result.get(0).getTrackerTypes().get(0).getTrackerType());

        verify(petRepository, times(1)).findAllByInZoneFalse();
    }
}