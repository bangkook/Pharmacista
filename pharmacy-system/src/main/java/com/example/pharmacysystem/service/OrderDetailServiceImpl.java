package com.example.pharmacysystem.service;

import com.example.pharmacysystem.dto.OrderDetailDTO;
import com.example.pharmacysystem.model.OrderDetail;
import com.example.pharmacysystem.repository.OrderDetailRepository;
import com.example.pharmacysystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    public List<OrderDetailDTO> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);

        // If an order is empty, it means something went wrong or given id does not exist
        if (orderDetails.isEmpty())
            throw new RuntimeException();

        return orderDetails.stream()
                .map(orderDetail ->
                        new OrderDetailDTO(orderDetail.getOrderId(),
                                productRepository.getReferenceById(orderDetail.getProductSN()),
                                orderDetail.getQuantity())
                )
                .collect(Collectors.toList());
    }
}
