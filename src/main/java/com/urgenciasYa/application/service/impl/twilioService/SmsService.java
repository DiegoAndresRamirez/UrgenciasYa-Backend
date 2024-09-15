package com.urgenciasYa.application.service.impl.twilioService;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private final String fromPhoneNumber = "+16313378201";

    public void sendSms(String toPhoneNumber, String body) {
        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                body
        ).create();
    }
}
