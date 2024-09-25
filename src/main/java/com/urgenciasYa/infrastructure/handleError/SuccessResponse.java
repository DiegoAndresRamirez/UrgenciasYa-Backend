package com.urgenciasYa.infrastructure.handleError;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

// Class to represent a successful response
@Getter
@Setter
@Builder
public class SuccessResponse {
    private Integer code;  // HTTP status code of the success response
    private String status; // Status description of the response (e.g., "OK")
    private String message; // Message providing additional details about the response
}
