package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.OrderDetail;
import com.example.pharmacysystem.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);

        // If an order is empty, it means something went wrong or given id does not exist
        if (orderDetails.isEmpty())
            throw new RuntimeException();

        return orderDetails;
    }
}
