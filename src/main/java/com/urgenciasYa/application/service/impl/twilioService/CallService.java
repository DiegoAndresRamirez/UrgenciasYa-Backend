//package com.urgenciasYa.application.service.impl.twilioService;
//
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Call;
//import com.twilio.type.PhoneNumber;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CallService {
//
//    @Value("${twilio.phone-number}")
//    private String fromPhoneNumber;
//
//    @Value("${twilio.twiml-url}")
//    private String twimlUrl;
//
//    public void makeCall(String toPhoneNumber) {
//        try {
//            Call.creator(
//                    new PhoneNumber(toPhoneNumber),
//                    new PhoneNumber(fromPhoneNumber),
//                    twimlUrl
//            ).create();
//            System.out.println("Call initiated to " + toPhoneNumber);
//        } catch (Exception e) {
//            System.err.println("Failed to initiate call to " + toPhoneNumber);
//            e.printStackTrace();
//        }
//    }
//}