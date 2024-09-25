package com.urgenciasYa.infrastructure.handleError;

import com.urgenciasYa.application.exceptions.ErrorSimple;
import com.urgenciasYa.application.exceptions.ErrorsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice

public class GlobalExceptionHandler {

    // Handle MethodArgumentNotValidException, which occurs when validation fails
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorSimple badRequest (MethodArgumentNotValidException exception){

        List<String> errors = new ArrayList<>(); // Create a list to hold error messages

        exception.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));

        return ErrorsResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .errors(errors)
                .build();
    }

    // Handle IllegalArgumentException, which can be thrown for various invalid argument cases
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ErrorSimple conflict(IllegalArgumentException exception){
        return ErrorSimple.builder()
                .code(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT.name())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorSimple internalServerError(Exception exception){
        return ErrorSimple.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message(exception.getMessage())
                .build();
    }


}
