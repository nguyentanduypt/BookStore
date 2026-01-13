package com.example.bookstore.services;

import com.example.bookstore.models.dto.CustomerDTO;
import com.example.bookstore.models.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(String id);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO , String id);
    void deleteUser(String id);
}
