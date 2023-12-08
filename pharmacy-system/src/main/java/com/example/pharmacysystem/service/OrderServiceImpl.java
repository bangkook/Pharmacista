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

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByDateCreatedDesc();
    }

    public List<Order> getOrdersForUser(int userId) {
        return orderRepository.findByUserId(userId);
    }

}
