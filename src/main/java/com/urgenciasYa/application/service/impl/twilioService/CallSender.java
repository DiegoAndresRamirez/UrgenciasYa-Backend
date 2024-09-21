package com.urgenciasYa.application.service.impl.twilioService;

public interface CallSender {
    void makeCall(String toPhoneNumber, String messageUrl);
}