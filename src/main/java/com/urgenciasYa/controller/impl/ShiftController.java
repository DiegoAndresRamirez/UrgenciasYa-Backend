package com.urgenciasYa.controller.impl;

import com.urgenciasYa.model.Shift;
import com.urgenciasYa.service.Impl.ShiftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @PostMapping("/create")
    @Operation(
            summary = "Crear un turno de urgencia",
            description = "Permite a los usuarios solicitar un turno en el hospital asignado por su EPS. Devuelve los detalles del turno creado.",
            tags = {"Shift"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turno creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Usuario, hospital o EPS no encontrado")
    })
    public ResponseEntity<Shift> createShift(
            @Parameter(description = "Número de documento del usuario") @RequestParam String document,
            @Parameter(description = "ID del hospital") @RequestParam Long hospitalId,
            @Parameter(description = "ID de la EPS") @RequestParam Integer epsId
    ) {
        try {
            Shift shift = shiftService.createShift(document, hospitalId, epsId);
            return new ResponseEntity<>(shift, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
