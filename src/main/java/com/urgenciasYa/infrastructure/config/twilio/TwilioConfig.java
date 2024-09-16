package com.urgenciasYa.infrastructure.config.twilio;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {
    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;


    public void initializeTwilio() {
        Twilio.init(accountSid, authToken);
    }
}
