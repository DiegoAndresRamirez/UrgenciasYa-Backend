package com.urgenciasYa.controller.impl;

import com.urgenciasYa.controller.interfaces.IModelHospital;
import com.urgenciasYa.dto.request.HospitalCreateResponseDTO;
import com.urgenciasYa.dto.request.HospitalSearchRequestDTO;
import com.urgenciasYa.dto.response.HospitalCardDTO;
import com.urgenciasYa.model.Hospital;
import com.urgenciasYa.service.Impl.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
}
