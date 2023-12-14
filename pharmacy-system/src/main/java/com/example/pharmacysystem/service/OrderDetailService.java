package com.example.pharmacysystem.service;

import com.example.pharmacysystem.dto.OrderDetailDTO;
import com.example.pharmacysystem.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetailDTO> getOrderDetailsByOrderId(int orderId);

    boolean createOrderDetails(OrderDetail orderDetail);
}
