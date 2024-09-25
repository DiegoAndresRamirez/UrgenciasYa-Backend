package com.urgenciasYa.application.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/*
 * Represents a detailed error response.
 * This class extends the basic error structure provided by ErrorSimple.
 */

@Getter
@Setter
@SuperBuilder
public class ErrorResponse extends ErrorSimple {
    private String message; // Detailed error message
}
