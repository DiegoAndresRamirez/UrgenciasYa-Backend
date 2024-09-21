package com.urgenciasYa.application.service.impl.twilioService;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    // Credenciales para SMS
    private final String accountSidSms = "AC182fe5cb319e4c1ba3515f0b3e8bafb1";
    private final String authTokenSms = "ae5e92a0c120790631d2855b53a525b1";
    private final String phoneNumberSms = "+16313378201"; // Número de Twilio para SMS

    public TwilioSmsSender() {
        Twilio.init(accountSidSms, authTokenSms); // Inicializar Twilio para SMS
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        if (isPhoneNumberValid(phoneNumber)) {
            PhoneNumber to = new PhoneNumber(phoneNumber);
            PhoneNumber from = new PhoneNumber(phoneNumberSms);

            try {
                MessageCreator creator = Message.creator(to, from, message);
                creator.create();
                LOGGER.info("SMS sent to {}: {}", phoneNumber, message);
            } catch (Exception e) {
                LOGGER.error("Failed to send SMS: {}", e.getMessage());
                throw new RuntimeException("Failed to send SMS", e);
            }
        } else {
            throw new IllegalArgumentException("Phone number [" + phoneNumber + "] is not a valid number");
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // Implementación de la validación del número de teléfono
        return true; // Cambia esto según tus necesidades
    }
}
