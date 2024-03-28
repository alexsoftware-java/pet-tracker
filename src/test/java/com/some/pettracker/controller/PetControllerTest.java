package com.some.pettracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.some.pettracker.dto.*;
import com.some.pettracker.entity.CatEntity;
import com.some.pettracker.service.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PetService petService;

    @Test
    void testReceivePetData() throws Exception {
        // given
        PetDto petDto = new PetDto();
        petDto.setPetType(PetType.CAT);
        petDto.setTrackerType(TrackerType.SMALL_CAT);
        petDto.setOwnerId(1);
        petDto.setInZone(true);
        petDto.setLostTracker(false);
        CatEntity savedPetEntity = new CatEntity();
        savedPetEntity.setId(1L);
        savedPetEntity.setPetType(petDto.getPetType());
        savedPetEntity.setTrackerType(petDto.getTrackerType());
        savedPetEntity.setOwnerId(petDto.getOwnerId());
        savedPetEntity.setInZone(petDto.getInZone());
        savedPetEntity.setLostTracker(false);

        when(petService.savePet(any(PetDto.class))).thenReturn(savedPetEntity);
        // when
        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.petType").value("CAT"))
                .andExpect(jsonPath("$.trackerType").value("SMALL_CAT"))
                .andExpect(jsonPath("$.ownerId").value(1))
                .andExpect(jsonPath("$.inZone").value(true))
                .andExpect(jsonPath("$.lostTracker").value(false));

        verify(petService, times(1)).savePet(any(PetDto.class));
    }

    @Test
    void testGetAllPetsByOwnerId() throws Exception {
        int ownerId = 1;
        PetDto petDto = new PetDto();
        petDto.setPetType(PetType.DOG);
        petDto.setTrackerType(TrackerType.SMALL_DOG);
        petDto.setOwnerId(ownerId);
        petDto.setInZone(true);
        petDto.setLostTracker(false);

        when(petService.getAllPetsByOwnerId(ownerId)).thenReturn(Collections.singletonList(petDto));

        mockMvc.perform(get("/api/pets/owner/{ownerId}", ownerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].petType").value("DOG"))
                .andExpect(jsonPath("$[0].trackerType").value("SMALL_DOG"))
                .andExpect(jsonPath("$[0].ownerId").value(ownerId))
                .andExpect(jsonPath("$[0].inZone").value(true))
                .andExpect(jsonPath("$[0].lostTracker").value(false));

        verify(petService, times(1)).getAllPetsByOwnerId(ownerId);
    }

    @Test
    void testGetPetsOutsideZone() throws Exception {
        // given
        PetDto catPetDto = new PetDto();
        catPetDto.setPetType(PetType.CAT);
        catPetDto.setTrackerType(TrackerType.SMALL_CAT);
        catPetDto.setOwnerId(1);
        catPetDto.setInZone(false);
        catPetDto.setLostTracker(false);
        TrackerTypeDto trackerTypeDto = new TrackerTypeDto(TrackerType.SMALL_CAT, List.of(catPetDto));
        PetTypeDto petTypeDto = new PetTypeDto(PetType.CAT, Collections.singletonList(trackerTypeDto));
        when(petService.getPetsOutsideZone()).thenReturn(Collections.singletonList(petTypeDto));

        // when
        mockMvc.perform(get("/api/pets/outside"))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].petType").value("CAT"))
                .andExpect(jsonPath("$[0].trackerTypes[0].trackerType").value("SMALL_CAT"))
                .andExpect(jsonPath("$[0].trackerTypes[0].pets[0].petType").value("CAT"))
                .andExpect(jsonPath("$[0].trackerTypes[0].pets[0].ownerId").value(1))
                .andExpect(jsonPath("$[0].trackerTypes[0].pets[0].inZone").value(false))
                .andExpect(jsonPath("$[0].trackerTypes[0].pets[0].lostTracker").value(false));

        verify(petService, times(1)).getPetsOutsideZone();
    }

    @Test
    void testNotFound() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pets/owner/{ownerId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isNotFound());
    }

    @Test
    void testBadRequest() throws Exception {
        // when
        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "petType": "BIRD",
                                  "trackerType": "SMALL_CAT",
                                  "ownerId": 1,
                                  "inZone": true,
                                  "lostTracker": false
                                }"""))
                // then
                .andExpect(status().isBadRequest());
    }
}