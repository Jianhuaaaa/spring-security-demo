package com.jsun.security.service;

import com.jsun.security.dto.UserRequestDTO;
import com.jsun.security.dto.UserResponseDTO;
import com.jsun.security.entity.Role;
import com.jsun.security.exception.BusinessException;
import com.jsun.security.exception.ResourceNotFoundException;
import com.jsun.security.entity.User;
import com.jsun.security.repository.RoleRepository;
import com.jsun.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    // 在UserServiceImpl中注入PasswordEncoder
    private final PasswordEncoder passwordEncoder;

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

        // 获取持久化的USER角色
        Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));

        // 构建用户实体（添加密码字段）
        User user = User.builder()
                .username(userRequestDTO.getUsername())
                .email(userRequestDTO.getEmail())
                .fullName(userRequestDTO.getFullName())
                .password(passwordEncoder.encode("123456")) // default value
                .build();
        // 直接添加角色，不维护反向关联（避免递归）
        user.getRoles().add(userRole);

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

    // 映射实体到DTO
    private UserResponseDTO mapToResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


}
