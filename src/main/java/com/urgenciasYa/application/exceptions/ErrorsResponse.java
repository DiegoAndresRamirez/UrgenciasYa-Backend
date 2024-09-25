package com.urgenciasYa.application.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.List;

/*
 * Represents a detailed error response that can contain multiple error messages.
 * This class extends from ErrorSimple, allowing it to inherit basic error properties.
 */

@Getter
@Setter
@SuperBuilder
public class ErrorsResponse extends ErrorSimple {
    private List<String> errors; // A list of error messages providing more detail on the issues encountered
}
