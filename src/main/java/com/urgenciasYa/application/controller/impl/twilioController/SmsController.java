package com.urgenciasYa.application.controller.impl.twilioController;

import com.urgenciasYa.application.service.impl.twilioService.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestParam String to, @RequestParam String body) {
        try {
            smsService.sendSms(to, body);
            return ResponseEntity.ok("Message sent");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message");
        }
    }
}
