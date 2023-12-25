package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    List<Order> getOrdersForUser(int userId);

    boolean createOrder(Order order);
}
