package com.example.bookstore.dto;
import lombok.Data;
@Data
public class ReviewDTO {
    private String id;
    private String customerId;
    private String productId;
    private String product_reviews;
    private String reviewDate;
}
