package com.urgenciasYa.controller.impl;

import com.urgenciasYa.controller.interfaces.IModelEps;
import com.urgenciasYa.dto.response.EpsDTO;
import com.urgenciasYa.model.Eps;
import com.urgenciasYa.service.IModel.IEpsModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/eps")
public class EpsController implements IModelEps {

    @Autowired
    IEpsModel epsService;

    @Override
    @Operation( summary = "Retrieves a list of all EPS entities.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List obtained successfully"),
            @ApiResponse(responseCode = "404", description = "No eps found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<List<EpsDTO>> getAllEps() {
        List<Eps> eps = epsService.readALl();
        List<EpsDTO> epsDTOS = eps.stream()
                .map(e -> EpsDTO.builder()
                        .name(e.getName())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(epsDTOS);
    }
}
