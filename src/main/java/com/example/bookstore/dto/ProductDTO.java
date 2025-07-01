package com.example.bookstore.dto;
import lombok.Data;
@Data
public class ProductDTO {
    private String id;
    private String title;
    private String author;
    private String description;
    private Double price;
    private Integer Quantity;
    private String image;
    private String status;
    private String categoryId;
}
