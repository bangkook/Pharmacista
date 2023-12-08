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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailServiceTest {

    @Autowired
    private OrderDetailService orderDetailService;

    @MockBean
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void getOrderDetailsByOrderId_OrderExists() {

        OrderDetail orderDetail1 = new OrderDetail(1, 1, 4);
        OrderDetail orderDetail2 = new OrderDetail(1, 5, 2);

        // Mocking the repository behavior
        when(orderDetailRepository.findByOrderId(1)).thenReturn(
                Arrays.asList(orderDetail1, orderDetail2));

        List<OrderDetail> result = orderDetailService.getOrderDetailsByOrderId(1);

        // Verifying that the repository method was called
        verify(orderDetailRepository, times(1)).findByOrderId(1);

        // Asserting the result
        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getOrderId());
        assertEquals(1, result.get(0).getProductId());
        assertEquals(4, result.get(0).getQuantity());

        assertEquals(1, result.get(1).getOrderId());
        assertEquals(5, result.get(1).getProductId());
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
}
