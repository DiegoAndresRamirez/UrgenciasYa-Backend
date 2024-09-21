package com.urgenciasYa.application.service.impl.twilioService;

import com.urgenciasYa.application.dto.request.SmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private final SmsSender smsSender;

    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSms(String phoneNumber, String message) {
        smsSender.sendSms(phoneNumber, message);
    }
}