package com.urgenciasYa.infrastructure.config.twilio;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account-sid}")
    private String smsAccountSid;

    @Value("${twilio.auth-token}")
    private String smsAuthToken;

    @Value("${twilio.account-sid-call}")
    private String callAccountSid;

    @Value("${twilio.auth-token-call}")
    private String callAuthToken;

    @Bean
    public TwilioInitializer smsTwilioInitializer() {
        return new TwilioInitializer(smsAccountSid, smsAuthToken);
    }

    @Bean
    public TwilioInitializer callTwilioInitializer() {
        return new TwilioInitializer(callAccountSid, callAuthToken);
    }

    // Clase interna para inicializar Twilio
    public static class TwilioInitializer {
        public TwilioInitializer(String accountSid, String authToken) {
            Twilio.init(accountSid, authToken);
        }
    }
}