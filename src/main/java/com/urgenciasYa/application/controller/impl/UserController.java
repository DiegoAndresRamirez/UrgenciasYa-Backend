package com.urgenciasYa.application.controller.impl;

import com.urgenciasYa.application.controller.interfaces.IModelUser;
import com.urgenciasYa.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.application.dto.response.LoginDTO;
import com.urgenciasYa.application.exceptions.ErrorsResponse;
import com.urgenciasYa.domain.model.UserEntity;
import com.urgenciasYa.infrastructure.handleError.SuccessResponse;
import com.urgenciasYa.application.exceptions.ErrorSimple;
import com.urgenciasYa.application.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Tag(name = "User")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController implements IModelUser {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @Operation(
            summary = "Register new user",
            description = "Este endpoint crea un nuevo usuario en el sistema usando la información proporcionada en el DTO."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, posiblemente debido a datos inválidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    public ResponseEntity<?> create(
            @Parameter(description = "Información del nuevo usuario") @RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        try {
            userService.create(userRegisterDTO);
            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.CREATED.value())
                    .status(HttpStatus.CREATED.name())
                    .message("Usuario creado con éxito")
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (IllegalArgumentException exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST.name())
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimple);
        } catch (Exception exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("Error interno del servidor: " + exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody UserEntity user) {

        try {
            LoginDTO loginDTO = userService.verify(user);
            return ResponseEntity.status(HttpStatus.OK).body(loginDTO);
        } catch (Exception e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .status(HttpStatus.UNAUTHORIZED.name())
                    .message("Autenticación fallida")
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorSimple);
        }
    }

}
