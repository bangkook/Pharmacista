package com.example.pharmacysystem.repository;

import com.example.pharmacysystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(int userId);

    List<Order> findAllByOrderByDateCreatedDesc();
}
