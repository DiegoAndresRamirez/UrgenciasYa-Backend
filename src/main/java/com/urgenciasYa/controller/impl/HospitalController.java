package com.urgenciasYa.controller.impl;

import com.urgenciasYa.dto.request.HospitalSearchRequestDTO;
import com.urgenciasYa.dto.response.HospitalCardDTO;
import com.urgenciasYa.model.Hospital;
import com.urgenciasYa.service.Impl.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
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
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Operation( summary = "Retrieve a list of hospitals based on EPS, town, latitude, and longitude.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List obtained successfully"),
            @ApiResponse(responseCode = "404", description = "No hospital found"),
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
}
