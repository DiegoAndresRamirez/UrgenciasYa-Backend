package com.urgenciasYa.hexagonal.application.controller.impl;

import com.urgenciasYa.hexagonal.application.controller.interfaces.IModelHospital;
import com.urgenciasYa.hexagonal.application.dto.request.HospitalCreateResponseDTO;
import com.urgenciasYa.hexagonal.application.dto.request.HospitalSearchRequestDTO;
import com.urgenciasYa.hexagonal.application.dto.response.HospitalCardDTO;
import com.urgenciasYa.hexagonal.domain.model.Hospital;
import com.urgenciasYa.hexagonal.application.service.impl.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hospitals")
@CrossOrigin(origins = "http://localhost:3000")
public class HospitalController implements IModelHospital {

    @Autowired
    private HospitalService hospitalService;

    @Operation(
            summary = "Retrieve a list of hospitals based on EPS, town, latitude, and longitude.",
            description = "Fetches a list of hospitals that match the specified EPS, town, latitude, and longitude parameters. Returns a list of hospitals as cards with details.",
            parameters = {
                    @Parameter(name = "eps", description = "EPS code to filter hospitals", required = true),
                    @Parameter(name = "town", description = "Town to filter hospitals", required = true),
                    @Parameter(name = "latitude", description = "Latitude for location-based search", required = true),
                    @Parameter(name = "longitude", description = "Longitude for location-based search", required = true)
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List obtained successfully"),
            @ApiResponse(responseCode = "404", description = "No hospital found matching the criteria"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<List<HospitalCardDTO>> getHospitalByEpsAndTown(
            @RequestParam String eps,
            @RequestParam String town,
            @RequestParam double latitude,
            @RequestParam double longitude
    ) {
        HospitalSearchRequestDTO requestDTO = new HospitalSearchRequestDTO(eps, town, latitude, longitude);

        List<HospitalCardDTO> hospitals = hospitalService.getHospitalsNearby(requestDTO);

        if (hospitals.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(hospitals, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a new hospital",
            description = "Creates a new hospital record in the system using the provided hospital data. Returns the created hospital object if successful.",
            requestBody = @RequestBody(
                    description = "Hospital data for creating a new record",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HospitalCreateResponseDTO.class)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Hospital created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Possibly due to invalid data in the provided DTO"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Override
    @PostMapping("/create")
    public ResponseEntity<Hospital> create(@RequestBody HospitalCreateResponseDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.create(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @Operation(
            summary = "Update an existing hospital",
            description = "Updates an existing hospital record identified by the given ID with the provided data. Returns the updated hospital object if successful.",
            requestBody = @RequestBody(
                    description = "Hospital data for updating an existing record",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HospitalCreateResponseDTO.class)
                    )
            ),
            parameters = {
                    @Parameter(name = "id", description = "ID of the hospital to be updated", required = true)
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hospital updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Possibly due to invalid data in the provided DTO or hospital not found"),
            @ApiResponse(responseCode = "404", description = "Hospital not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<Hospital> update(
            @RequestBody HospitalCreateResponseDTO dto,
            @PathVariable Long id) {
        try {
            Hospital updatedHospital = hospitalService.update(id, dto);
            return ResponseEntity.ok(updatedHospital);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(
            summary = "Delete a hospital by its ID",
            description = "Deletes the hospital record identified by the given ID. Returns a success message if the operation is successful.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the hospital to be deleted", required = true)
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Hospital deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Hospital not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            hospitalService.delete(id);
            return ResponseEntity.ok("Hospital with ID " + id + " deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Retrieve a hospital by its ID",
            description = "Fetches the hospital record identified by the given ID. Returns the hospital details if found.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the hospital to retrieve", required = true)
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hospital retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Hospital not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getById(@PathVariable Long id) {
        try {
            Hospital hospital = hospitalService.getById(id);
            return ResponseEntity.ok(hospital);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Retrieve all hospitals",
            description = "Fetches a list of all hospitals. Returns an empty list if no hospitals are found."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of hospitals retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Override
    @GetMapping("/all")
    public ResponseEntity<List<Hospital>> getAll() {
        try {
            List<Hospital> hospitals = this.hospitalService.readALl();
            return ResponseEntity.ok(hospitals);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
