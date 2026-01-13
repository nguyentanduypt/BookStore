package com.example.bookstore.models.dto;
import lombok.Data;
@Data
public class OrderDetailDTO {
    private String id;
    private String orderId;
    private String productId;
    private String  quantity;
    private String  totalPrice;
}
