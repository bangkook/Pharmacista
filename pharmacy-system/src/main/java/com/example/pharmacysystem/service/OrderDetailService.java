package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetail> getOrderDetailsByOrderId(int orderId);
}
