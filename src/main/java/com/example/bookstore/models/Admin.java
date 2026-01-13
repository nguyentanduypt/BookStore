package com.example.bookstore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "\"admin\"")
@Data
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User {
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Không được để trống")
    private String departmentName;
}
