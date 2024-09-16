package com.urgenciasYa.application.service.impl.twilioService;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SmsService {


    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    @Value("${twilio.phone-number-sms}")
    private String fromPhoneNumber;

    public void sendSms(String toPhoneNumber, String body) {
        try {
            Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(fromPhoneNumber),
                    body
            ).create();
            logger.info("SMS sent to {}", toPhoneNumber);
        } catch (Exception e) {
            logger.error("Failed to send SMS to {}", toPhoneNumber, e);
        }
    }
}

