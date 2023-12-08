package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.Order;
import com.example.pharmacysystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    // TODO : Sort orders by date
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersForUser(int userId) {
        return orderRepository.getOrdersForUser(userId);
    }

}
