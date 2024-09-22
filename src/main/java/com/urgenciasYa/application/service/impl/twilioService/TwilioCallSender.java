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

    // Credenciales actualizadas para llamadas
    private final String accountSidCall = "ACebde10402bb52b4c7e625eb5b330f177"; // Nuevo Account SID
    private final String authTokenCall = "7189e24acf7e00fc8e7ced23ae8c5039"; // Nuevo Auth Token
    private final String phoneNumberCall = "+13345648026"; // Nuevo número de Twilio para llamadas

    public TwilioCallSender() {
        Twilio.init(accountSidCall, authTokenCall); // Inicializar Twilio para llamadas
    }

    @Override
    public void makeCall(String toPhoneNumber, String messageUrl) {
        PhoneNumber from = new PhoneNumber(phoneNumberCall); // Usar el número de Twilio para llamadas

        // Usar el SID de TwiML para la llamada
        String twimlUrl = "https://handler.twilio.com/twiml/EH86d452bcf3ed262855dd018987a2183b";

        try {
            Call call = Call.creator(
                    new PhoneNumber(toPhoneNumber),
                    from,
                    new com.twilio.type.Twiml("<Response><Say>Iniciando llamada de emergencia.</Say></Response>") // Mensaje temporal
            ).create();

            LOGGER.info("Call initiated to {}: {}", toPhoneNumber, call.getSid());
        } catch (Exception e) {
            LOGGER.error("Failed to make call: {}", e.getMessage());
            throw new RuntimeException("Failed to make call", e);
        }
    }
}
