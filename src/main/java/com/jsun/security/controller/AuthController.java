package com.jsun.security.controller;

import com.jsun.security.dto.ApiResponse;
import com.jsun.security.dto.LoginRequestDTO;
import com.jsun.security.dto.UserRequestDTO;
import com.jsun.security.dto.UserResponseDTO;
import com.jsun.security.service.UserDetailsServiceImpl;
import com.jsun.security.service.UserService;
import com.jsun.security.utils.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "Authentication and authorization operations")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ApiResponse<String> authenticate(@RequestBody LoginRequestDTO loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ApiResponse.success(jwt);
    }

    @PostMapping("/register")
    public ApiResponse<UserResponseDTO> register(@RequestBody UserRequestDTO userRequest) {
        UserResponseDTO createdUser = userService.createUser(userRequest);
        return ApiResponse.created(createdUser);
    }

}
