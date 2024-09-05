package com.urgenciasYa.controller.impl;

import com.urgenciasYa.model.Hospital;
import com.urgenciasYa.service.Impl.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hospital/api/v1")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/findByEpsAndTown")
    public ResponseEntity<List<Hospital>> getHospitalByEpsAndTown(
            @RequestParam String eps,
            @RequestParam String town
    ){
        List<Hospital> hospitals = hospitalService.findHospitalByEpsAndTown(eps, town);
        if(hospitals.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(hospitals, HttpStatus.OK);
    }
}
