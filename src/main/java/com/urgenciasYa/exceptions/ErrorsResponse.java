package com.urgenciasYa.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ErrorsResponse extends ErrorSimple {
    private String message;
}
