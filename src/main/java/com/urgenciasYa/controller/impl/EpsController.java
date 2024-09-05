package com.urgenciasYa.controller.impl;

import com.urgenciasYa.controller.interfaces.IModelEps;
import com.urgenciasYa.dto.response.EpsDTO;
import com.urgenciasYa.model.Eps;
import com.urgenciasYa.service.IModel.IEpsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
