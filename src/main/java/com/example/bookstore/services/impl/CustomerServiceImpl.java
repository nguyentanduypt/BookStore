package com.example.bookstore.services.impl;

import com.example.bookstore.enums.CustomerStatus;
import com.example.bookstore.enums.UserStatus;
import com.example.bookstore.models.APIResponse;
import com.example.bookstore.models.Customer;
import com.example.bookstore.models.Pagination;
import com.example.bookstore.models.Role;
import com.example.bookstore.models.dto.CustomerDTO;
import com.example.bookstore.repositories.CustomerRepository;
import com.example.bookstore.services.CustomerService;
import com.example.bookstore.services.RoleService;
import com.example.bookstore.services.exceptions.error.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    @Override
    public Pagination<CustomerDTO> getAllCustomer(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage  = customerRepository.findAll(pageable);

        List<CustomerDTO> CustomerDTOs = customerPage.getContent()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());

        Pagination<CustomerDTO> pagination = new Pagination<>();
        pagination.setContent(CustomerDTOs);
        pagination.setPage(customerPage.getNumber());
        pagination.setSize(customerPage.getSize());
        pagination.setTotalPages(customerPage.getTotalPages());
        pagination.setTotalElements(customerPage.getTotalElements());

        return pagination;
    }

    @Override
    public CustomerDTO getCustomerById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Customer> optionalCustomer = customerRepository.findById(uuid);
        return optionalCustomer.map(customer -> modelMapper.map(customer, CustomerDTO.class)).orElse(null);

    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Role role = roleService.getRoleByName("USER");
        if (customerRepository.existsByEmail(customerDTO.getEmail().trim())){
            throw new DuplicateResourceException("Email không hợp lệ");
        }
        if (customerRepository.existsByPhoneNumber(customerDTO.getPhoneNumber().trim())) {
            throw new DuplicateResourceException("Phone Number không hợp lệ");
        }
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        customer.setRole(role);
        customer.setCreatedTime(Instant.now());
        customer.setUpdatedTime(Instant.now());
        customer.setStatus(UserStatus.ACTIVE);
        customer.setCustomerType(CustomerStatus.REGULAR_CUSTOMER); // ??

        return modelMapper.map(customerRepository.save(customer), CustomerDTO.class);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Customer existingCustomer = customerRepository.findById(uuid).orElse(null);
        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
//        existingAdmin.setAvatar(adminDTO.getAvatar());
        existingCustomer.setPassword(customerDTO.getPassword());
        existingCustomer.setCustomerType(CustomerStatus.valueOf(customerDTO.getCustomerType()));//??
        existingCustomer.setStatus(UserStatus.valueOf(customerDTO.getStatus()));

        return modelMapper.map(customerRepository.save(existingCustomer), CustomerDTO.class);
    }

    @Override
    public void deleteCustomer(String id) {
    UUID uuid = UUID.fromString(id);
    Customer existingCustomer = customerRepository.findById(uuid).orElse(null);
    customerRepository.delete(existingCustomer);
    }

    @Override
    public List<CustomerDTO> getCustomerByName(String name) {
        List<Customer> customers = customerRepository.findCustomerByName(name);
        return customers.stream()
                .map(learner -> modelMapper.map(learner, CustomerDTO.class) )
                .collect(Collectors.toList());
    }

}
