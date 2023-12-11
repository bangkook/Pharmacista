package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.model.Order;
import com.example.pharmacysystem.model.OrderDetail;
import com.example.pharmacysystem.service.OrderDetailService;
import com.example.pharmacysystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/orders")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class OrdersListController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    // TODO : return OrderDTO with username added
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();

    }

    @GetMapping("/{userId}")
    public List<Order> getOrdersForUser(@PathVariable("userId") int userId) {
        return orderService.getOrdersForUser(userId);
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByOrderId(@PathVariable("orderId") int orderId) {
        try {
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(orderId);
            return new ResponseEntity<>(orderDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
