package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.Order;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public interface OrderService {

    boolean createOrder(Order order);
}
