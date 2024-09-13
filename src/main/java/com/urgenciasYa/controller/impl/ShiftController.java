package com.urgenciasYa.controller.impl;

import com.urgenciasYa.model.Shift;
import com.urgenciasYa.service.Impl.ShiftService;
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
    public ResponseEntity<Shift> createShift(
            @RequestParam String document,
            @RequestParam Long hospitalId,
            @RequestParam Integer epsId
    ) {
        try {
            Shift shift = shiftService.createShift(document, hospitalId, epsId);
            return new ResponseEntity<>(shift, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
