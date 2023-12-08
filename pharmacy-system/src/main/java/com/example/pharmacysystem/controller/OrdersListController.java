package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.model.Order;
import com.example.pharmacysystem.model.OrderDetail;
import com.example.pharmacysystem.service.OrderDetailService;
import com.example.pharmacysystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/orders")
@RestController
public class OrdersListController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();

    }

    @GetMapping("/{userId}")
    public List<Order> getOrdersForUser(@PathVariable("userId") int userId) {
        return orderService.getOrdersForUser(userId);
    }

    @GetMapping("/{orderId}/details")
    public List<OrderDetail> getOrderDetailsByOrderId(@PathVariable("orderId") int orderId) {
        return orderDetailService.getOrderDetailsByOrderId(orderId);
    }

}
