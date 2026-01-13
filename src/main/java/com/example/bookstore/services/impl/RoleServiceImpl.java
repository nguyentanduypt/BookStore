package com.example.bookstore.services.impl;

import com.example.bookstore.models.Role;
import com.example.bookstore.models.dto.RoleDTO;
import com.example.bookstore.repositories.RoleRepository;
import com.example.bookstore.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Role> optionalRole = roleRepository.findById(uuid);
        return optionalRole.map(role -> modelMapper.map(role, RoleDTO.class)).orElse(null);
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        role.setName(roleDTO.getName());
        return modelMapper.map(roleRepository.save(role), RoleDTO.class);
    }

    @Override
    public RoleDTO updateRole(String id, RoleDTO roleDTO) {
        UUID uuid = UUID.fromString(id);
        Role existingRole = roleRepository.findById(uuid).orElse(null);
        existingRole.setName(roleDTO.getName());
        Role updatedRole = roleRepository.save(existingRole);
        return modelMapper.map(updatedRole, RoleDTO.class);
    }



    @Override
    public void deleteRole(String id) {
        UUID uuid = UUID.fromString(id);
        Role existingRole = roleRepository.findById(uuid).orElse(null);
        roleRepository.delete(existingRole);
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = roleRepository.findByName(name);
        return role;
    }
}