package com.example.bookstore.models.dto;
import lombok.Data;
@Data
public class ReviewDTO {
    private String id;
    private String customerId;
    private String productId;
    private String productName;
    private String customerName;
    private String comment;
    private String reviewDate;
}
