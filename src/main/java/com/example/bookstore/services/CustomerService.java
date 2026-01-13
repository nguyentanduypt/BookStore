package com.example.bookstore.services;

import com.example.bookstore.models.Pagination;
import com.example.bookstore.models.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    Pagination<CustomerDTO> getAllCustomer(int page, int size);

    CustomerDTO getCustomerById(String id);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO, String id);

    void deleteCustomer(String id);

    List<CustomerDTO> getCustomerByName(String name);

}
