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

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByDateCreatedDesc();
    }

    @Override
    public List<Order> getOrdersForUser(int userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public boolean createOrder(Order order) {
        try {
            orderRepository.save(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
