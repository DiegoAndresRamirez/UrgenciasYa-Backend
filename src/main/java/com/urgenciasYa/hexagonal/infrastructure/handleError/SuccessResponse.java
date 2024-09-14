package com.urgenciasYa.hexagonal.infrastructure.handleError;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SuccessResponse {
    private Integer code;
    private String status;
    private String message;
}
