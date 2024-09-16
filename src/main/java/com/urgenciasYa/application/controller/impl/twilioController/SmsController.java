package com.urgenciasYa.application.controller.impl.twilioController;

import com.urgenciasYa.application.exceptions.ErrorSimple;
import com.urgenciasYa.application.service.impl.twilioService.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    @Operation(
            summary = "Send an SMS message",
            description = "Sends an SMS message to the specified recipient with the given body.",
            parameters = {
                    @Parameter(name = "to", description = "The recipient phone number", required = true),
                    @Parameter(name = "body", description = "The body of the SMS message", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Message sent successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = "string", example = "Message sent"))),
                    @ApiResponse(responseCode = "400", description = "Bad Request, if the request parameters are invalid",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorSimple.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error, if something goes wrong",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorSimple.class)))
            }
    )
    public ResponseEntity<String> sendSms(@RequestParam String to, @RequestParam String body) {
        try {
            smsService.sendSms(to, body);
            return ResponseEntity.ok("Message sent");
        } catch (IllegalArgumentException e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST.name())
                    .message("Invalid parameters: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimple.toString());
        } catch (Exception e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("Failed to send message: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple.toString());
        }
    }
}
