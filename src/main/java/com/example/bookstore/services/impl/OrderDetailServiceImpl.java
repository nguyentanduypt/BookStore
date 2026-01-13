package com.example.bookstore.services.impl;

import com.example.bookstore.models.Order;
import com.example.bookstore.models.OrderDetail;
import com.example.bookstore.models.Product;
import com.example.bookstore.models.dto.OrderDetailDTO;
import com.example.bookstore.repositories.OrderDetailRepository;
import com.example.bookstore.repositories.OrderRepository;
import com.example.bookstore.repositories.ProductRepository;
import com.example.bookstore.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailrepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderDetailDTO> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailrepository.findAll();
        return orderDetails.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailDTO getOrderDetailById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<OrderDetail> optionalOrderDetail = orderDetailrepository.findById(uuid);
        return optionalOrderDetail.map(orderDetail -> modelMapper
                .map(orderDetail, OrderDetailDTO.class))
                .orElse(null);
    }

    @Override
    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        UUID orderUUID = UUID.fromString(orderDetailDTO.getOrderId());
        UUID productId = UUID.fromString(orderDetailDTO.getProductId());

        Order order = orderRepository.findById(orderUUID).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);

        OrderDetail orderDetail = modelMapper.map(orderDetailDTO, OrderDetail.class);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setTotalPrice(BigDecimal.valueOf(Double.parseDouble(orderDetailDTO.getTotalPrice())));
        return modelMapper.map(orderDetailrepository.save(orderDetail), OrderDetailDTO.class);
    }

    @Override
    public OrderDetailDTO updateOrderDetail(OrderDetailDTO orderDetailDTO, String id) {
        UUID uuid = UUID.fromString(id);
        OrderDetail existingOrderDetail = orderDetailrepository.findById(uuid).orElse(null);
        existingOrderDetail
                .setTotalPrice(BigDecimal.valueOf(Double.parseDouble(orderDetailDTO.getTotalPrice())));
        return modelMapper.map(orderDetailrepository.save(existingOrderDetail), OrderDetailDTO.class);
    }

    @Override
    public void deleteOrderDetail(String id) {
        UUID uuid = UUID.fromString(id);
        OrderDetail existingOrderDetail = orderDetailrepository.findById(uuid).orElse(null);
        orderDetailrepository.delete(existingOrderDetail);

    }

    @Override
    public List<OrderDetailDTO> getOrderDetailsByOrderId(String orderId) {
        UUID uuid = UUID.fromString(orderId);
        List<OrderDetail> orderDetailList = orderDetailrepository.getOrderDetailsByOrderId(uuid);
        return orderDetailList.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class) )
                .collect(Collectors.toList());
    }
}
