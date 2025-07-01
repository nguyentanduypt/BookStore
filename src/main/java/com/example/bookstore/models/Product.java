package com.example.bookstore.models;

import com.example.bookstore.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"product\"")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String author;
    private String description;
    private Double price;
    private Integer Quantity;
    private String image;
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Review> review;
}
