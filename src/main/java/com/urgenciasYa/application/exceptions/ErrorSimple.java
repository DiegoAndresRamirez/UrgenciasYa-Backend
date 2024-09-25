package com.urgenciasYa.application.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/*
 * Represents a basic structure for error responses.
 * This class can be extended to create more detailed error responses.
 */

@Getter
@Setter
@SuperBuilder
public class ErrorSimple {
    private Integer code; // Error code indicating the type of error
    private String status; // Status of the error (e.g., "error", "fail")
    private String message; // Brief description of the error
}
