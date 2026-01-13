package com.example.bookstore.repositories;

import com.example.bookstore.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    List<OrderDetail> getOrderDetailsByOrderId(UUID orderId);
}