package com.urgenciasYa.application.service.impl.twilioService;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import com.urgenciasYa.infrastructure.config.twilio.TwilioConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        if (isPhoneNumberValid(phoneNumber)) {
            PhoneNumber to = new PhoneNumber(phoneNumber);
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getPhoneNumberSms());

            try {
                MessageCreator creator = Message.creator(to, from, message);
                creator.create();
                LOGGER.info("SMS sent to {}: {}", phoneNumber, message);
            } catch (Exception e) {
                LOGGER.error("Failed to send SMS: {}", e.getMessage());
                throw new RuntimeException("Failed to send SMS", e); // Propagar la excepción original
            }
        } else {
            throw new IllegalArgumentException("Phone number [" + phoneNumber + "] is not a valid number");
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // Implementación de la validación del número de teléfono
        return true;
    }
}