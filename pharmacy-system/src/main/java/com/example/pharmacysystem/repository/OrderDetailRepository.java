package com.example.pharmacysystem.repository;

import com.example.pharmacysystem.model.OrderDetail;
import com.example.pharmacysystem.utils.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    
    List<OrderDetail> findByOrderId(int orderId);
}
