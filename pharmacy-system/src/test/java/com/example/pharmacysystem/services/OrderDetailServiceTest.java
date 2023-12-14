package com.example.pharmacysystem.services;

import com.example.pharmacysystem.dto.OrderDetailDTO;
import com.example.pharmacysystem.model.OrderDetail;
import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.repository.OrderDetailRepository;
import com.example.pharmacysystem.repository.ProductRepository;
import com.example.pharmacysystem.service.OrderDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailServiceTest {

    @Autowired
    private OrderDetailService orderDetailService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void getOrderDetailsByOrderId_OrderExists() {
        Product product1 = new Product("1", 20F, Date.valueOf("2022-02-02"), Date.valueOf("2025-02-02"),
                "description", 10, "product1", "photo1");
        Product product2 = new Product("5", 10F, Date.valueOf("2021-01-01"), Date.valueOf("2024-01-01"),
                "description", 5, "product2", "photo2");

        OrderDetail orderDetail1 = new OrderDetail(1, "1", 4);
        OrderDetail orderDetail2 = new OrderDetail(1, "5", 2);

        // Mocking the repository behavior
        when(orderDetailRepository.findByOrderId(1)).thenReturn(
                Arrays.asList(orderDetail1, orderDetail2));
        when(productRepository.findById("1")).thenReturn(java.util.Optional.of(product1));
        when(productRepository.findById("5")).thenReturn(java.util.Optional.of(product2));

        List<OrderDetailDTO> result = orderDetailService.getOrderDetailsByOrderId(1);

        // Verifying that the repository method was called
        verify(orderDetailRepository, times(1)).findByOrderId(1);
        verify(productRepository, times(1)).findById("1");
        verify(productRepository, times(1)).findById("5");

        // Asserting the result
        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getOrderId());
        assertEquals(product1.toString(), result.get(0).getProduct().toString());
        assertEquals(4, result.get(0).getQuantity());

        assertEquals(1, result.get(1).getOrderId());
        assertEquals(product2.toString(), result.get(1).getProduct().toString());
        assertEquals(2, result.get(1).getQuantity());
    }

    @Test
    public void getOrderDetailsByOrderId_OrderDoesNotExist_ShouldThrowException() {
        int nonExistingOrderId = 1;

        // Mocking the repository behavior
        when(orderDetailRepository.findByOrderId(1)).thenReturn(Collections.emptyList());

        // Asserting the result
        assertThrows(RuntimeException.class, () -> orderDetailService.getOrderDetailsByOrderId(nonExistingOrderId));

        // Verifying that the repository method was called
        verify(orderDetailRepository, times(1)).findByOrderId(nonExistingOrderId);
    }

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
