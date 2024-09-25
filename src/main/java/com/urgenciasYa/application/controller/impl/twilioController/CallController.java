package com.urgenciasYa.application.controller.impl.twilioController;

import com.urgenciasYa.application.service.impl.twilioService.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Controller for managing Twilio call operations.
 * This class handles HTTP requests related to making calls.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/calls")
public class CallController {
    private final CallService callService;

    //Constructs a CallController with the specified CallService.

    @Autowired
    public CallController(CallService callService) {
        this.callService = callService;
    }

    //Initiates a call to the specified phone number with the provided message URL.

    @PostMapping
    public void makeCall(@RequestParam String toPhoneNumber, @RequestParam String messageUrl) {
        callService.initiateCall(toPhoneNumber, messageUrl);
    }

    // Still in development.
}
