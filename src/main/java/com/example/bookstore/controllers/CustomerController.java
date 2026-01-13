package com.example.bookstore.controllers;


import com.example.bookstore.models.APIResponse;
import com.example.bookstore.models.Pagination;
import com.example.bookstore.models.dto.CustomerDTO;
import com.example.bookstore.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping  ("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    //laasy taat ca
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Pagination<CustomerDTO>>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pagination<CustomerDTO> customers = customerService.getAllCustomer(page, size);
        APIResponse<Pagination<CustomerDTO>> response = new APIResponse<>(
                "success",
                "Learners retrieved successfully",
                customers,
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
    //lấy theo id
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<CustomerDTO>> getCustomerById(@PathVariable String id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        APIResponse<CustomerDTO> response = new APIResponse<>(
                "success",
                "Customer retrieved successfully",
                customer,
                null,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    //Tạo
    @PostMapping()
    public ResponseEntity<APIResponse<CustomerDTO>> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO created = customerService.createCustomer(customerDTO);
        APIResponse<CustomerDTO> response = new APIResponse<>(
                "success",
                "Customer created successfully",
                created,
                null,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //chỉnh sửa
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<CustomerDTO>> updateCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updated = customerService.updateCustomer(customerDTO, id);
        APIResponse<CustomerDTO> response = new APIResponse<>(
                "success",
                "Customer updated successfully",
                updated,
                null,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    //Xóa Admin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Void>> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Customer deleted successfully",
                null,
                null,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
    //tìm kiếm customer theo name
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<List<CustomerDTO>>> searchLearner (@RequestParam String name){
        List<CustomerDTO> CustomerDTO = customerService.getCustomerByName(name);
        APIResponse<List<CustomerDTO>> response = new APIResponse<>(
                "success",
                "Learner retrieved successfully",
                CustomerDTO,
                null,
                LocalDateTime.now());
        return ResponseEntity.status(200).body(response);
    }
}
