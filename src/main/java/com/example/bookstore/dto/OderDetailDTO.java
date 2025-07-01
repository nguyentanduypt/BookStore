package com.example.bookstore.dto;
import lombok.Data;
@Data
public class OderDetailDTO {
    private String id;
    private String orderId;
    private String productId;
    private Integer quantity;
    private Double totalPrice;
}
