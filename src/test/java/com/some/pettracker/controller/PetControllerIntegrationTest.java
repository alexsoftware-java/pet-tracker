package com.some.pettracker.controller;

import com.some.pettracker.dto.PetType;
import com.some.pettracker.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PetControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetRepository petRepository;

    @Test
    void testAddAndGetPet() throws Exception {
        // add new cat data
        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "petType": "CAT",
                                  "trackerType": "SMALL_CAT",
                                  "ownerId": 1,
                                  "inZone": true,
                                  "lostTracker": false
                                }"""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.petType").value("CAT"))
                .andExpect(jsonPath("$.trackerType").value("SMALL_CAT"))
                .andExpect(jsonPath("$.ownerId").value(1))
                .andExpect(jsonPath("$.inZone").value(true))
                .andExpect(jsonPath("$.lostTracker").value(false));
        // check cat in DB
        var inDB = petRepository.findByOwnerId(1);
        assertEquals(1, inDB.size());
        assertEquals(PetType.CAT, inDB.get(0).getPetType());
        // get this cat via api
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pets/owner/{ownerId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].petType").value("CAT"))
                .andExpect(jsonPath("$[0].trackerType").value("SMALL_CAT"))
                .andExpect(jsonPath("$[0].ownerId").value(1))
                .andExpect(jsonPath("$[0].inZone").value(true))
                .andExpect(jsonPath("$[0].lostTracker").value(false));
    }

}