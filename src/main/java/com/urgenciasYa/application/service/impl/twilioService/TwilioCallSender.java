package com.urgenciasYa.application.service.impl.twilioService;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("twilioCall")
public class TwilioCallSender implements CallSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioCallSender.class);

    // Credenciales para llamadas
    private final String accountSidCall = "AC17f6054551c60da406d0de23b1992b8b";
    private final String authTokenCall = "de2118e1311819881f90f1fac56af5ea";
    private final String phoneNumberCall = "+13344012823"; // Número de Twilio para llamadas

    public TwilioCallSender() {
        Twilio.init(accountSidCall, authTokenCall); // Inicializar Twilio para llamadas
    }

    @Override
    public void makeCall(String toPhoneNumber, String messageUrl) {
        PhoneNumber from = new PhoneNumber(phoneNumberCall); // Usar el número de Twilio para llamadas

        try {
            Call call = Call.creator(
                    new PhoneNumber(toPhoneNumber),
                    from,
                    new com.twilio.type.Twiml("<Response><Say>" + messageUrl + "</Say></Response>")
            ).create();

            LOGGER.info("Call initiated to {}: {}", toPhoneNumber, call.getSid());
        } catch (Exception e) {
            LOGGER.error("Failed to make call: {}", e.getMessage());
            throw new RuntimeException("Failed to make call", e);
        }
    }
}
