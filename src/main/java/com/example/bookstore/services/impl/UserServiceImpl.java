package com.example.bookstore.services.impl;


import com.example.bookstore.enums.UserStatus;
import com.example.bookstore.models.Role;
import com.example.bookstore.models.User;
import com.example.bookstore.models.dto.UserDTO;
import com.example.bookstore.repositories.UserRepository;
import com.example.bookstore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public UserDTO getUserById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<User> optionalUser  = userRepository.findById(uuid);
        return optionalUser.map(user-> modelMapper.map(user, UserDTO.class)).orElse(null);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        Role role = roleService.getRoleByName("USER");
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(role);
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPassword(userDTO.getPassword());
        user.setCreatedTime(Instant.now());
        user.setUpdatedTime(Instant.now());
        user.setStatus(UserStatus.ACTIVE);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO ,String id) {
        UUID uuid = UUID.fromString(id);
        User existingUser = userRepository.findById(uuid).orElse(null);
        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setRole(roleService.getRoleByName(userDTO.getName()));
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
//        existingUser.setAvatar(userDTO.getAvatar());
        existingUser.setStatus(UserStatus.valueOf(userDTO.getStatus()));
        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public void deleteUser(String id) {
        UUID uuid = UUID.fromString(id);
        User existingUser = userRepository.findById(uuid).orElse(null);
        userRepository.delete(existingUser);
    }
}
