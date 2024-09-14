package com.urgenciasYa.hexagonal.application.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ErrorResponse extends ErrorSimple {
    private String message;
}
