package com.urgenciasYa.application.service.impl.twilioService;


public interface SmsSender {
    void sendSms(String phoneNumber, String message);
}