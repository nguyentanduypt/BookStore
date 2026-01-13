package com.example.bookstore.models;

import com.example.bookstore.enums.CategoryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"category\"")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, columnDefinition = "VARCHAR(200)")
    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(min = 1, message = "Tên danh mục phải từ 1 đến 200 ký tự")
    private String name;
    @Column(columnDefinition = "ENUM('ACTIVE','INACTIVE')")
    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    private String description;

//    private CategoryStatus status;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<Product> products;
}