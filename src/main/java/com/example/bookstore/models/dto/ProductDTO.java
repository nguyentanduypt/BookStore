package com.example.bookstore.models.dto;
import lombok.Data;
@Data
public class ProductDTO {
    private String id;
    private String name;
    private String title;
    private String author;
    private String categoryName;
    private String description;
    private Double price;
    private int totalReviews;
    private double averageRating;
    private Integer Quantity;
    private String image;
    private String status;
//    private String categoryId;
}
