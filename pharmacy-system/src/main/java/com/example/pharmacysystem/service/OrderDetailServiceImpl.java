package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.OrderDetail;
import com.example.pharmacysystem.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public boolean createOrderDetails(OrderDetail orderDetail){
        try {
            orderDetailRepository.save(orderDetail);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
