package com.jsun.security.service;

import com.jsun.security.dto.UserRequestDTO;
import com.jsun.security.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    void deleteUser(Long id);
}
