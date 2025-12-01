package com.jsun.security.service;

import com.jsun.security.dto.UserRequestDTO;
import com.jsun.security.dto.UserResponseDTO;
import com.jsun.security.exception.BusinessException;
import com.jsun.security.exception.ResourceNotFoundException;
import com.jsun.security.model.User;
import com.jsun.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToResponseDTO(user);
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        // business validation
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new BusinessException("Username already exists: " + userRequestDTO.getUsername());
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new BusinessException("Email already exists: " + userRequestDTO.getEmail());
        }
        // construct user
        User user = User.builder()
                .username(userRequestDTO.getUsername())
                .email(userRequestDTO.getEmail())
                .fullName(userRequestDTO.getFullName())
                .build();
        // save user
        User savedUser = userRepository.save(user);
        return mapToResponseDTO(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // business validation
        if (
                !existingUser.getUsername().equals(userRequestDTO.getUsername())
                        && userRepository.existsByUsername(userRequestDTO.getUsername())
        ) {
            throw new BusinessException("Username already exists: " + userRequestDTO.getUsername());
        }

        if (!existingUser.getEmail().equals(userRequestDTO.getEmail())
                && userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new BusinessException("Email already exists: " + userRequestDTO.getEmail());
        }

        // update user info
        existingUser.setUsername(userRequestDTO.getUsername());
        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setFullName(userRequestDTO.getFullName());

        User updatedUser = userRepository.save(existingUser);
        return mapToResponseDTO(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


}
