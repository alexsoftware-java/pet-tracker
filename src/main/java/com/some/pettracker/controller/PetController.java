package com.some.pettracker.controller;

import com.some.pettracker.dto.PetDto;
import com.some.pettracker.dto.PetTypeDto;
import com.some.pettracker.entity.PetEntity;
import com.some.pettracker.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@Validated
public class PetController {

    private final PetService petService;

    @PostMapping
    @Operation(summary = "Receive Pet Data", description = "Endpoint to receive pet tracking data")
    @ApiResponse(responseCode = "200", description = "Pet data received successfully")
    public ResponseEntity<PetEntity> receivePetData(@Valid @RequestBody PetDto petDto) {
        return ResponseEntity.ok(petService.savePet(petDto));
    }

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Get All Pets By Owner ID", description = "Endpoint to get all pets by owner ID")
    @ApiResponse(responseCode = "200", description = "List of all pets",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = """
                            [
                              {
                                "petType": "CAT",
                                "trackerType": "SMALL_CAT",
                                "ownerId": 1,
                                "inZone": true,
                                "lostTracker": false
                              }
                            ]"""
                    )))
    @ApiResponse(responseCode = "404", description = "No pets found for the specified owner ID")
    public ResponseEntity<List<PetDto>> getAllPetsByOwnerId(@PathVariable int ownerId) {
        List<PetDto> pets = petService.getAllPetsByOwnerId(ownerId);
        if (pets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/outside")
    @Operation(summary = "Get All Pets Outside Zone", description = "Endpoint to get all pets outside the power saving zone")
    @ApiResponse(responseCode = "200", description = "List of all pets outside the power saving zone")
    public ResponseEntity<List<PetTypeDto>> getPetsOutsideZone() {
        return ResponseEntity.ok(petService.getPetsOutsideZone());
    }
}