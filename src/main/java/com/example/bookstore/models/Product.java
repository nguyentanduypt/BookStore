package com.example.bookstore.models;

import com.example.bookstore.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"product\"")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @NotEmpty(message = "Tên sản phẩm không được để trống")
    @Column(nullable = false, unique = true)
    private String name;

    private String title;

    @NotBlank(message = "Author ko dc để trống")
    private String author;

    @NotNull
    @NotEmpty(message = "detailDesc không được để trống")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @NotNull
    @DecimalMin(value = "1", inclusive = false, message = "Giá sản phẩm phải lớn hơn 0")
    private Double price;

    @NotNull
    @Min(value = 1, message = "Số lượng sản phẩm phải lớn hơn 0")
    private Integer quantity;

    private String image;

    private Instant createdTime;

    private Instant updatedTime;

//    private Instant updatedBy;
    @Column(nullable = false)
    private Double averageRating;

    @Column(nullable = false)
    private Integer totalReviews;

    @Column(columnDefinition = "ENUM('ACTIVE','INACTIVE','OUT_OF_STOCK')")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Review> review;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<OrderDetail> orderDetail;

}
