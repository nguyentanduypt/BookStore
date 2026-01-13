package com.example.bookstore.services;

import com.example.bookstore.models.Role;
import com.example.bookstore.models.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> getAllRoles();
    RoleDTO getRoleById(String id);
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO updateRole(String id, RoleDTO roleDTO);
    void deleteRole(String id);
    Role getRoleByName(String name);
}
