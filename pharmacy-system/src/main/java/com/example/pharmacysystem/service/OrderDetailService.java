package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.Order;
import com.example.pharmacysystem.model.OrderDetail;
import org.springframework.stereotype.Service;

@Service
public interface OrderDetailService {

    boolean createOrderDetails(OrderDetail orderDetail);

}
