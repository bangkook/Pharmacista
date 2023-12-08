package com.example.pharmacysystem.repositories;

import com.example.pharmacysystem.model.Order;
import com.example.pharmacysystem.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void getOrdersForUserTest() {
        String str1 = "2022-03-31";
        String str2 = "2022-05-04";
        int user1 = 1;
        int user2 = 2;
        Order order1 = new Order(user1, Date.valueOf(str1), 100.0F);
        Order order2 = new Order(user1, Date.valueOf(str2), 50.0F);
        Order order3 = new Order(user2, Date.valueOf(str2), 150.0F);

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        List<Order> resultUser1 = orderRepository.getOrdersForUser(user1);

        // Asserting the result
        assertEquals(2, resultUser1.size());

        assertEquals(user1, resultUser1.get(0).getUserId());
        assertEquals(Date.valueOf(str1), resultUser1.get(0).getDateCreated());
        assertEquals(100.0F, resultUser1.get(0).getTotalPrice(), 0);

        assertEquals(user1, resultUser1.get(1).getUserId());
        assertEquals(Date.valueOf(str2), resultUser1.get(1).getDateCreated());
        assertEquals(50.0F, resultUser1.get(1).getTotalPrice(), 0);

        // Same with user 2
        List<Order> resultUser2 = orderRepository.getOrdersForUser(user2);

        // Asserting the result
        assertEquals(1, resultUser2.size());

        assertEquals(user2, resultUser2.get(0).getUserId());
        assertEquals(Date.valueOf(str2), resultUser2.get(0).getDateCreated());
        assertEquals(150.0F, resultUser2.get(0).getTotalPrice(), 0);

        orderRepository.delete(order1);
        orderRepository.delete(order2);
        orderRepository.delete(order3);

    }

    @Test
    public void getOrdersForUserTest_Empty() {
        int user = 10;

        List<Order> result = orderRepository.getOrdersForUser(user);

        // Asserting the result
        assertEquals(0, result.size());
    }

}
