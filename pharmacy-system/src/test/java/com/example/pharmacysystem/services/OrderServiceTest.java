package com.example.pharmacysystem.services;

import com.example.pharmacysystem.model.Order;
import com.example.pharmacysystem.repository.OrderDetailRepository;
import com.example.pharmacysystem.repository.OrderRepository;
import com.example.pharmacysystem.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void testCreateOrder_SuccessfulCreation() {

        Order order = new Order();

        when(orderRepository.save(order)).thenReturn(order);

        boolean result = orderService.createOrder(order);

        assertTrue(result);
    }

    @Test
    public void testCreateOrder_FailedCreation() {

        Order order = new Order();

        when(orderRepository.save(order)).thenThrow(new RuntimeException("Creation failed"));

        boolean result = orderService.createOrder(order);

        assertFalse(result);

    }
}
