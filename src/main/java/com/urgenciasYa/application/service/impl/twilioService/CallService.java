package com.urgenciasYa.application.service.impl.twilioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CallService {
    private final CallSender callSender;

    @Autowired
    public CallService(@Qualifier("twilioCall") CallSender callSender) {
        this.callSender = callSender;
    }

    public void initiateCall(String toPhoneNumber, String messageUrl) {
        callSender.makeCall(toPhoneNumber, messageUrl);
    }
}
