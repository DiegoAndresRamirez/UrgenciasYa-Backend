package com.urgenciasYa.application.controller.impl;

import com.urgenciasYa.application.controller.interfaces.IModelUser;
import com.urgenciasYa.application.dto.request.LoginRequestDTO;
import com.urgenciasYa.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.application.dto.response.LoginDTO;
import com.urgenciasYa.application.dto.response.UserResponseDTO;
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

import java.util.List;

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
            description = "This endpoint creates a new user in the system using the information provided in the DTO."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, possibly due to invalid data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    public ResponseEntity<?> create(
            @Parameter(description = "Information of the new user") @RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        try {
            userService.create(userRegisterDTO);
            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.CREATED.value())
                    .status(HttpStatus.CREATED.name())
                    .message("User created successfully")
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
                    .message("Internal server error: " + exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }




    @PostMapping("/login")
    @Operation(
            summary = "Login a user",
            description = "This endpoint allows a user to authenticate in the system using their credentials. It returns a session token or session information upon success."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginDTO.class))),
            @ApiResponse(responseCode = "401", description = "Authentication failed, incorrect credentials",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    public ResponseEntity<?> login(
            @Parameter(description = "User credentials") @RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(loginRequestDTO.getName());
            userEntity.setPassword(loginRequestDTO.getPassword());

            LoginDTO loginDTO = userService.verify(userEntity);
            return ResponseEntity.status(HttpStatus.OK).body(loginDTO);
        } catch (Exception e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .status(HttpStatus.UNAUTHORIZED.name())
                    .message("Authentication failed: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorSimple);
        }
    }



    @GetMapping
    @Operation(
            summary = "Retrieve all users",
            description = "This endpoint allows you to obtain a list of all registered users in the system. The response includes detailed information about each user in DTO format.",
            operationId = "getAllUsers" // Adding a unique ID for this endpoint
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of users obtained successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    public ResponseEntity<?> getAll() {
        try {
            List<UserResponseDTO> users = userService.readAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("Error obtaining the list of users: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }



    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a user",
            description = "This endpoint allows you to delete a user from the system using their ID. If the user is found and deleted successfully, a response with no content (204 No Content) is returned. If the user is not found, a 404 Not Found error is returned. In case of an unexpected error, a 500 Internal Server Error is returned."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully, no content in the response"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message("User with ID " + id + " not found: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
        } catch (Exception e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("Error deleting user: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve a user by ID",
            description = "This endpoint allows you to obtain a specific user using their ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRegisterDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            UserRegisterDTO userDTO = userService.getById(id);
            return ResponseEntity.ok(userDTO);
        } catch (IllegalArgumentException e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
        } catch (Exception e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("Error retrieving the user: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing user",
            description = "This endpoint allows you to update an existing user using their ID and the information provided in the DTO."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, possibly due to invalid data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID of the user to update") @PathVariable Long id,
            @Parameter(description = "Updated information of the user") @RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        try {
            userService.update(id, userRegisterDTO);

            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK.name())
                    .message("User updated successfully")
                    .build();

            return ResponseEntity.ok(successResponse);
        } catch (IllegalArgumentException e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
        } catch (Exception e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("Error updating the user: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }

}
