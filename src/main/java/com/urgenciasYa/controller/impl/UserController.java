package com.urgenciasYa.controller.impl;

import com.urgenciasYa.controller.handleError.SuccessResponse;
import com.urgenciasYa.controller.interfaces.IModelUser;
import com.urgenciasYa.dto.request.UserRegisterDTO;
import com.urgenciasYa.exceptions.ErrorResponse;
import com.urgenciasYa.exceptions.ErrorSimple;
import com.urgenciasYa.exceptions.ErrorsResponse;
import com.urgenciasYa.service.IModel.IUserModel;
import com.urgenciasYa.service.Impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User")
public class UserController implements IModelUser {

    @Autowired
    UserService userService;

    @Operation(summary = "Register new user",description = "Este endpoint crea un nuevo usuario en el sistema usando la información proporcionada en el DTO.")
    @ApiResponse(responseCode = "201",description = "Esto sale cuando el usuario es registrado con exito",
    content = {@Content(mediaType = "aplication/json",schema = @Schema(type ="String",example = "Usuario creado con exito"))})
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, posiblemente debido a datos inválidos",
            content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ErrorsResponse.class))})
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json"))

    @Override
    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        try{
            userService.create(userRegisterDTO);
            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.CREATED.value())
                    .status(HttpStatus.CREATED.name())
                    .message("Registro exitoso")
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        }catch (IllegalArgumentException exception){
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.CONFLICT.value())
                    .status(HttpStatus.CONFLICT.name())
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorSimple);
        }catch(Exception exception){
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }
}
