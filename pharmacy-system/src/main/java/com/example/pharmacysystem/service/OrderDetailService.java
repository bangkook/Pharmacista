package com.example.pharmacysystem.service;

import com.example.pharmacysystem.dto.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetailDTO> getOrderDetailsByOrderId(int orderId);
}
