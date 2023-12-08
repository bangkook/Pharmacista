package com.example.pharmacysystem.controllers;

import com.example.pharmacysystem.model.Order;
import com.example.pharmacysystem.model.OrderDetail;
import com.example.pharmacysystem.service.OrderDetailService;
import com.example.pharmacysystem.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdersListControllerTest {

    @MockBean
    OrderService orderService;

    @MockBean
    OrderDetailService orderDetailService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getAllOrders() throws Exception {
        int user1 = 1;
        int user2 = 2;

        // Mocking the service behavior
        List<Order> orders = Arrays.asList(
                new Order(user1, Date.valueOf("2022-03-31"), 100.0F),
                new Order(user1, Date.valueOf("2022-04-25"), 1500.0F),
                new Order(user2, Date.valueOf("2023-03-31"), 50.0F));

        when(orderService.getAllOrders()).thenReturn(orders);

        // Performing the request
        MvcResult result = mockMvc.perform(get("/orders/all"))
                .andExpect(status().isOk())
                .andReturn();

        // Verifying the response
        MockHttpServletResponse response = result.getResponse();
        List<Order> resultOrders = Arrays.asList(objectMapper.readValue(response.getContentAsString(), Order[].class));

        // Verifying that the service method was called
        verify(orderService, times(1)).getAllOrders();

        // Asserting the result
        assertEquals(orders.size(), resultOrders.size());

        // Assert for each item
        assertEquals(user1, resultOrders.get(0).getUserId());
        assertEquals(Date.valueOf("2022-03-31").toString(), resultOrders.get(0).getDateCreated().toString());
        assertEquals(100.0F, resultOrders.get(0).getTotalPrice(), 0);

        assertEquals(user1, resultOrders.get(1).getUserId());
        assertEquals(Date.valueOf("2022-04-25").toString(), resultOrders.get(1).getDateCreated().toString());
        assertEquals(1500.0F, resultOrders.get(1).getTotalPrice(), 0);

        assertEquals(user2, resultOrders.get(2).getUserId());
        assertEquals(Date.valueOf("2023-03-31").toString(), resultOrders.get(2).getDateCreated().toString());
        assertEquals(50.0F, resultOrders.get(2).getTotalPrice(), 0);

    }

    @Test
    public void getOrdersForUser_FoundOrders() throws Exception {
        int user1 = 1;
        Date date1 = Date.valueOf("2022-03-31");
        Date date2 = Date.valueOf("2022-04-25");

        // Mocking the service behavior
        List<Order> orders = Arrays.asList(
                new Order(user1, date1, 100.0F),
                new Order(user1, date2, 1500.0F));

        when(orderService.getOrdersForUser(user1)).thenReturn(orders);

        // Performing the request
        MvcResult result = mockMvc.perform(get("/orders/{userId}", user1))
                .andExpect(status().isOk())
                .andReturn();

        // Verifying the response
        MockHttpServletResponse response = result.getResponse();
        List<Order> resultOrders = Arrays.asList(objectMapper.readValue(response.getContentAsString(), Order[].class));

        // Verifying that the service method was called
        verify(orderService, times(1)).getOrdersForUser(user1);

        // Asserting the result
        assertEquals(orders.size(), resultOrders.size());

        // Assert for each item
        assertEquals(user1, resultOrders.get(0).getUserId());
        assertEquals(date1.toString(), resultOrders.get(0).getDateCreated().toString());
        assertEquals(100.0F, resultOrders.get(0).getTotalPrice(), 0);

        assertEquals(user1, resultOrders.get(1).getUserId());
        assertEquals(date2.toString(), resultOrders.get(1).getDateCreated().toString());
        assertEquals(1500.0F, resultOrders.get(1).getTotalPrice(), 0);
    }

    @Test
    public void getOrdersForUser_NoOrders() throws Exception {
        int user1 = 1;

        when(orderService.getOrdersForUser(user1)).thenReturn(Collections.emptyList());

        // Performing the request
        MvcResult result = mockMvc.perform(get("/orders/{userId}", user1))
                .andExpect(status().isOk())
                .andReturn();

        // Verifying the response
        MockHttpServletResponse response = result.getResponse();
        List<Order> resultOrders = Arrays.asList(objectMapper.readValue(response.getContentAsString(), Order[].class));

        // Verifying that the service method was called
        verify(orderService, times(1)).getOrdersForUser(user1);

        // Asserting the result
        assertTrue(resultOrders.isEmpty());
    }

    @Test
    public void getOrderDetailsByOrderId_Exist() throws Exception {
        int orderId = 1;

        // Mocking the service behavior
        List<OrderDetail> orderDetails = Arrays.asList(
                new OrderDetail(orderId, "111aaa", 1),
                new OrderDetail(orderId, "222bbb", 3));

        when(orderDetailService.getOrderDetailsByOrderId(orderId)).thenReturn(orderDetails);

        // Performing the request
        MvcResult result = mockMvc.perform(get("/orders/{orderId}/details", orderId))
                .andExpect(status().isOk())
                .andReturn();

        // Verifying the response
        MockHttpServletResponse response = result.getResponse();
        List<OrderDetail> resultOrders = Arrays.asList(
                objectMapper.readValue(response.getContentAsString(), OrderDetail[].class));

        // Verifying that the service method was called
        verify(orderDetailService, times(1)).getOrderDetailsByOrderId(orderId);

        // Asserting the result
        assertEquals(orderDetails.size(), resultOrders.size());

        // Assert for each item
        assertEquals(orderId, resultOrders.get(0).getOrderId());
        assertEquals("111aaa", resultOrders.get(0).getProductSN());
        assertEquals(1, resultOrders.get(0).getQuantity());

        assertEquals(orderId, resultOrders.get(1).getOrderId());
        assertEquals("222bbb", resultOrders.get(1).getProductSN());
        assertEquals(3, resultOrders.get(1).getQuantity());
    }

    @Test
    public void getOrderDetailsByOrderId_Empty() throws Exception {
        int orderId = 1;

        when(orderDetailService.getOrderDetailsByOrderId(orderId)).thenReturn(Collections.emptyList());

        // Performing the request
        MvcResult result = mockMvc.perform(get("/orders/{orderId}/details", orderId))
                .andExpect(status().isOk())
                .andReturn();

        // Verifying the response
        MockHttpServletResponse response = result.getResponse();
        List<Order> resultOrders = Arrays.asList(objectMapper.readValue(response.getContentAsString(), Order[].class));

        // Verifying that the service method was called
        verify(orderDetailService, times(1)).getOrderDetailsByOrderId(orderId);

        // Asserting the result
        assertTrue(resultOrders.isEmpty());
    }
}
