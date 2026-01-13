package com.example.bookstore.services.impl;

import com.example.bookstore.enums.UserStatus;
import com.example.bookstore.models.Admin;
import com.example.bookstore.models.Role;
import com.example.bookstore.models.dto.AdminDTO;
import com.example.bookstore.repositories.AdminRepository;
import com.example.bookstore.services.AdminService;
import com.example.bookstore.services.exceptions.error.DuplicateResourceException;
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
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public List<AdminDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(admin -> modelMapper.map(admin, AdminDTO.class)).collect(Collectors.toList());

    }

    @Override
    public AdminDTO getAdminById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Admin> optionalAdmin = adminRepository.findById(uuid);
        return optionalAdmin.map(admin -> modelMapper.map(admin, AdminDTO.class)).orElse(null);
    }

    @Override
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        Role role = roleService.getRoleByName("ADMIN");
        if (adminRepository.existsByEmail(adminDTO.getEmail().trim())) {
            throw new DuplicateResourceException("Email không hợp lệ");
        }
            if (adminRepository.existsByPhoneNumber(adminDTO.getPhoneNumber().trim())) {
                throw new DuplicateResourceException("Số điện thoại không hợp lệ");
            }
            Admin admin = modelMapper.map(adminDTO, Admin.class);
            admin.setName(adminDTO.getName());
            admin.setEmail(adminDTO.getEmail());
            admin.setPhoneNumber(adminDTO.getPhoneNumber());
            admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
//            admin.setAvatar(adminDTO.getAvatar());
            admin.setRole(role);
             admin.setCreatedTime(Instant.now());
             admin.setUpdatedTime(Instant.now());
            admin.setStatus(UserStatus.ACTIVE);
            admin.setDepartmentName(adminDTO.getDepartmentName());
            return modelMapper.map(adminRepository.save(admin), AdminDTO.class);

        }

    @Override
    public AdminDTO updateAdmin(AdminDTO adminDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Admin existingAdmin = adminRepository.findById(uuid).orElse(null);
        existingAdmin.setName(adminDTO.getName());
        existingAdmin.setEmail(adminDTO.getEmail());
        existingAdmin.setPhoneNumber(adminDTO.getPhoneNumber());
//        existingAdmin.setAvatar(adminDTO.getAvatar());
        existingAdmin.setPassword(adminDTO.getPassword());
        existingAdmin.setDepartmentName(adminDTO.getDepartmentName());
        existingAdmin.setStatus(UserStatus.valueOf(adminDTO.getStatus()));
        return modelMapper.map(adminRepository.save(existingAdmin), AdminDTO.class);
    }

    @Override
    public void deleteAdmin(String id) {
        UUID uuid = UUID.fromString(id);
        Admin existingAdmin = adminRepository.findById(uuid).orElse(null);
        adminRepository.delete(existingAdmin);
    }
}