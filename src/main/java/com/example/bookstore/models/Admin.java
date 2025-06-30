package com.example.bookstore.models;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "\"admin\"")
@Data
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User {
    private String departmentName;
}
