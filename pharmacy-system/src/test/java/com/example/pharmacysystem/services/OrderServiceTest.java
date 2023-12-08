package com.example.pharmacysystem.services;

import com.example.pharmacysystem.model.Order;
import com.example.pharmacysystem.repository.OrderRepository;
import com.example.pharmacysystem.service.OrderService;
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
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void getAllOrders() {
        // Mocking the repository behavior
        when(orderRepository.findAllByOrderByDateCreatedDesc()).thenReturn(Arrays.asList(new Order(), new Order()));

        List<Order> result = orderService.getAllOrders();

        // Verifying that the repository method was called
        verify(orderRepository, times(1)).findAllByOrderByDateCreatedDesc();

        // Asserting the result
        assertEquals(2, result.size());
    }

    @Test
    public void getOrdersForUser_UserExists() {
        String str1 = "2022-06-31";
        String str2 = "2022-05-04";
        int user1 = 1;
        Order order1 = new Order(user1, Date.valueOf(str1), 100.0F);
        Order order2 = new Order(user1, Date.valueOf(str2), 50.0F);

        // Mocking the repository behavior
        when(orderRepository.findByUserId(user1)).thenReturn(Arrays.asList(order1, order2));

        List<Order> result = orderService.getOrdersForUser(user1);

        // Verifying that the repository method was called
        verify(orderRepository, times(1)).findByUserId(user1);

        // Asserting the result
        assertEquals(2, result.size());

        assertEquals(user1, result.get(0).getUserId());
        assertEquals(Date.valueOf(str1), result.get(0).getDateCreated());
        assertEquals(100.0F, result.get(0).getTotalPrice(), 0);

        assertEquals(user1, result.get(1).getUserId());
        assertEquals(Date.valueOf(str2), result.get(1).getDateCreated());
        assertEquals(50.0F, result.get(1).getTotalPrice(), 0);

    }

    @Test
    public void getOrdersForUser_NoOrders_ShouldReturnEmpty() {
        int userId = 1;

        // Mocking the repository behavior
        when(orderRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        List<Order> result = orderService.getOrdersForUser(userId);

        // Verifying that the repository method was called
        verify(orderRepository, times(1)).findByUserId(userId);

        // Asserting the result is empty
        assertEquals(0, result.size());
    }
}
