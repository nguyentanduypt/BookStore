package com.example.bookstore.dto;

import lombok.Data;
@Data
public class OderDTO {
    private String id;
    private String orderDate;
    private String customerId;
    private Double totalPrice;
    private String status;
}
