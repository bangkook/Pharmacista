package com.example.pharmacysystem.services;

import com.example.pharmacysystem.model.OrderDetail;
import com.example.pharmacysystem.repository.OrderDetailRepository;
import com.example.pharmacysystem.service.OrderDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailServiceTest {

    @MockBean
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Test
    public void testCreateOrderDetails_SuccessfulCreation() {

        OrderDetail orderDetail = new OrderDetail();

        when(orderDetailRepository.save(orderDetail)).thenReturn(orderDetail);

        boolean result = orderDetailService.createOrderDetails(orderDetail);

        assertTrue(result);

    }

    @Test
    public void testCreateOrderDetails_FailedCreation() {

        OrderDetail orderDetail = new OrderDetail();

        when(orderDetailRepository.save(orderDetail)).thenThrow(new RuntimeException("Creation failed"));

        boolean result = orderDetailService.createOrderDetails(orderDetail);

        assertFalse(result);

    }

}
