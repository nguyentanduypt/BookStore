package com.example.bookstore.models.dto;

import lombok.Data;
@Data
public class OrderDTO {
    private String id;
    private String orderDate;
    private String customerId;
    private Double totalPrice;
    private String status;
    private OrderDetailDTO orderDetail;
}
