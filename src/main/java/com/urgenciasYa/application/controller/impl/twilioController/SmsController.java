package com.urgenciasYa.application.controller.impl.twilioController;

import com.urgenciasYa.application.service.impl.twilioService.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Controller for managing SMS operations using Twilio.
 * This class handles HTTP requests related to sending SMS messages.
 */

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/sms")
public class SmsController {
    private final SmsService smsService;

    //Constructs an SmsController with the specified SmsService.

    @Autowired
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    // Sends an SMS message to the specified phone number.

    @PostMapping
    public void sendSms(@RequestParam String phoneNumber, @RequestParam String message) {
        smsService.sendSms(phoneNumber, message);
    }

    // still in development.
}
