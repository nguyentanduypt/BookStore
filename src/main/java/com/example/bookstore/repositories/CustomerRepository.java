package com.example.bookstore.repositories;

import com.example.bookstore.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
        boolean existsByEmail(String email);
        boolean existsByPhoneNumber(String phoneNumber);

        @Query("SELECT l FROM Customer l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :name, '%'))")
        List<Customer> findCustomerByName(@Param("name") String name);

}
