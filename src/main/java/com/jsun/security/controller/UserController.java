package com.jsun.security.controller;

import com.jsun.security.dto.ApiResponse;
import com.jsun.security.dto.UserRequestDTO;
import com.jsun.security.dto.UserResponseDTO;
import com.jsun.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "Operations related to user management")
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a single user by their ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user with the provided details")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input or duplicate username/email", content = @Content)
    })
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        UserResponseDTO createUser = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(ApiResponse.created(createUser));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an existing user by their ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input or duplicate username/email", content = @Content)
    })
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO updateUser = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(ApiResponse.success(updateUser));
    }

    /*@DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user", description = "Delete a user by their ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }*/
}
