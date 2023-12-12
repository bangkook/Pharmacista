package com.example.pharmacysystem.repositories;

import com.example.pharmacysystem.model.OrderDetail;
import com.example.pharmacysystem.repository.OrderDetailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
class OrderDetailRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Test
    void findByOrderIdTest() {
        OrderDetail orderDetail1 = new OrderDetail(1, "1", 4);
        OrderDetail orderDetail2 = new OrderDetail(1, "5", 2);
        OrderDetail orderDetail3 = new OrderDetail(2, "1", 1);

        entityManager.persist(orderDetail1);
        entityManager.persist(orderDetail2);
        entityManager.persist(orderDetail3);
        entityManager.flush();

        List<OrderDetail> result1 = orderDetailRepository.findByOrderId(1);

        // Asserting the result
        assertEquals(2, result1.size());

        assertEquals(1, result1.get(0).getOrderId());
        assertEquals("1", result1.get(0).getProductSN());
        assertEquals(4, result1.get(0).getQuantity());

        assertEquals(1, result1.get(1).getOrderId());
        assertEquals("5", result1.get(1).getProductSN());
        assertEquals(2, result1.get(1).getQuantity());

        // Repeat with user 2
        List<OrderDetail> result2 = orderDetailRepository.findByOrderId(2);

        // Asserting the result
        assertEquals(1, result2.size());

        assertEquals(2, result2.get(0).getOrderId());
        assertEquals("1", result2.get(0).getProductSN());
        assertEquals(1, result2.get(0).getQuantity());
    }

    @Test
    void findByOrderIdTest_Empty() {
        int orderId = 10;

        List<OrderDetail> result = orderDetailRepository.findByOrderId(orderId);

        // Asserting the result
        assertEquals(0, result.size());
    }
}
