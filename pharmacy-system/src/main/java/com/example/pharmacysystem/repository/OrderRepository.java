package com.example.pharmacysystem.repository;

import com.example.pharmacysystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(
            value = "Select * From orders where user_id = ?1",
            nativeQuery = true)
    List<Order> getOrdersForUser(int userId);
}
