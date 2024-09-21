package com.urgenciasYa.application.controller.impl.twilioController;

import com.urgenciasYa.application.service.impl.twilioService.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/calls")
public class CallController {
    private final CallService callService;

    @Autowired
    public CallController(CallService callService) {
        this.callService = callService;
    }

    @PostMapping
    public void makeCall(@RequestParam String toPhoneNumber, @RequestParam String messageUrl) {
        callService.initiateCall(toPhoneNumber, messageUrl);
    }
}
