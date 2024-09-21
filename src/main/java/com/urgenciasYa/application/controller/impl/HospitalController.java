package com.urgenciasYa.application.controller.impl;

import com.urgenciasYa.application.dto.request.HospitalSearchRequestDTO;
import com.urgenciasYa.application.dto.response.HospitalCardDTO;
import com.urgenciasYa.application.controller.interfaces.IModelHospital;
import com.urgenciasYa.application.dto.response.HospitalCreateResponseDTO;
import com.urgenciasYa.application.dto.response.HospitalGetResponseDTO;
import com.urgenciasYa.application.exceptions.ErrorSimple;
import com.urgenciasYa.domain.model.Hospital;
import com.urgenciasYa.application.service.impl.HospitalService;
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
                    @Parameter(name = "latitude", description = "Latitude for location-based search", required = false),
                    @Parameter(name = "longitude", description = "Longitude for location-based search", required = false)
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List obtained successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HospitalCardDTO.class))),
            @ApiResponse(responseCode = "404", description = "No hospitals found matching the criteria",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    @GetMapping("/filter")
    public ResponseEntity<?> getHospitalByEpsAndTown(
            @RequestParam String eps,
            @RequestParam String town,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude
    ) {
        HospitalSearchRequestDTO requestDTO;

        if (latitude != null && longitude != null) {
            requestDTO = new HospitalSearchRequestDTO(eps, town, latitude, longitude);
        } else {
            requestDTO = new HospitalSearchRequestDTO(eps, town, null, null);
        }

        try {
            List<HospitalCardDTO> hospitals = hospitalService.getHospitalsNearby(requestDTO);

            if (hospitals.isEmpty()) {
                ErrorSimple error = ErrorSimple.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND.name())
                        .message("No hospitals found matching the criteria.")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            return ResponseEntity.ok(hospitals);

        } catch (Exception e) {
            ErrorSimple error = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("An unexpected error occurred.")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
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
            @ApiResponse(responseCode = "201", description = "Hospital created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hospital.class))),
            @ApiResponse(responseCode = "400", description = "Bad request. Possibly due to invalid data in the provided DTO",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    @Override
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody HospitalCreateResponseDTO dto) {
        try {
            Hospital createdHospital = hospitalService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdHospital);
        } catch (IllegalArgumentException e) {
            ErrorSimple error = ErrorSimple.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST.name())
                    .message("Invalid data provided for hospital creation.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            ErrorSimple error = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("An unexpected error occurred while creating the hospital.")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
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
            @ApiResponse(responseCode = "200", description = "Hospital updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hospital.class))),
            @ApiResponse(responseCode = "400", description = "Bad request. Possibly due to invalid data in the provided DTO or hospital not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "404", description = "Hospital not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @RequestBody HospitalCreateResponseDTO dto,
            @PathVariable Long id) {
        try {
            Hospital updatedHospital = hospitalService.update(id, dto);
            return ResponseEntity.ok(updatedHospital);
        } catch (IllegalArgumentException e) {
            ErrorSimple error = ErrorSimple.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST.name())
                    .message("Invalid data provided for hospital update or hospital not found.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (EntityNotFoundException e) {
            ErrorSimple error = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message("Hospital with the given ID not found.")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorSimple error = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("An unexpected error occurred while updating the hospital.")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
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
            @ApiResponse(responseCode = "404", description = "Hospital not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            hospitalService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            ErrorSimple error = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message("Hospital with the given ID not found.")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorSimple error = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("An unexpected error occurred while deleting the hospital.")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
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
            @ApiResponse(responseCode = "200", description = "Hospital retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HospitalGetResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Hospital not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            HospitalGetResponseDTO hospital = hospitalService.getById(id);
            return ResponseEntity.ok(hospital);
        } catch (EntityNotFoundException e) {
            ErrorSimple error = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message("Hospital with the given ID not found.")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorSimple error = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("An unexpected error occurred while retrieving the hospital.")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }



    @Operation(
            summary = "Retrieve all hospitals",
            description = "Fetches a list of all hospitals. Returns an empty list if no hospitals are found."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of hospitals retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HospitalGetResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error. An unexpected error occurred while retrieving the hospitals.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        try {
            List<HospitalGetResponseDTO> hospitals = hospitalService.readALl();
            return ResponseEntity.ok(hospitals);
        } catch (Exception e) {
            ErrorSimple error = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("An unexpected error occurred while retrieving the hospitals.")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
