package com.example.bookstore.services.impl;


import com.example.bookstore.enums.OrderStatus;
import com.example.bookstore.models.Customer;
import com.example.bookstore.models.Order;
import com.example.bookstore.models.dto.OrderDTO;
import com.example.bookstore.models.dto.OrderDetailDTO;
import com.example.bookstore.repositories.CustomerRepository;
import com.example.bookstore.repositories.OrderRepository;
import com.example.bookstore.services.OrderDetailService;
import com.example.bookstore.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository oderRepository;
    private final CustomerRepository customerRepository;
    private final OrderDetailService orderDetailService;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = oderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Order> optionalOrder = oderRepository.findById(uuid);
        return optionalOrder.map(order -> modelMapper.map(order,OrderDTO.class)).orElse(null);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        UUID customerId = UUID.fromString(orderDTO.getCustomerId());
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(customer);
        Order savedOrder = oderRepository.save(order);
        if (savedOrder.getId() != null) {
            OrderDetailDTO detailDTO = orderDTO.getOrderDetail();
            detailDTO.setOrderId(savedOrder.getId().toString());
            orderDetailService.createOrderDetail(detailDTO);
        }
//
        return modelMapper.map(savedOrder, OrderDTO.class) ;
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Order existingOrder = oderRepository.findById(uuid).orElse(null);
        existingOrder.setStatus(OrderStatus.valueOf(orderDTO.getStatus()));
        return modelMapper.map(oderRepository.save(existingOrder), OrderDTO.class);
    }

    @Override
    public void deleteOrder(String id) {
           UUID uuid = UUID.fromString(id);
        Order existingOrder = oderRepository.findById(uuid).orElse(null);
        oderRepository.delete(existingOrder);
    }
}
