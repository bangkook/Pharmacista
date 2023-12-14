package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.Order;
import com.example.pharmacysystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public boolean createOrder(Order order){
        try {
            orderRepository.save(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
