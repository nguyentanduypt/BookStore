package com.example.bookstore.repositories;

import com.example.bookstore.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
     Category findByName(String name);
    // JpaRepository cung cấp đủ các phương thức CRUD cơ bản:
// save(), findById(), findAll(), deleteById(), existsById(), ...

}
