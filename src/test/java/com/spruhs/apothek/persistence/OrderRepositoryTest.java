package com.spruhs.apothek.persistence;

import com.spruhs.apothek.business.order.OrderJPA;
import com.spruhs.apothek.business.order.OrderStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void fillDatabase(){
        OrderJPA order1 = new OrderJPA(1L, 123L, 5, "TestApotheke1",
                LocalDate.parse("2020-01-05"), OrderStatus.RECEIVED);
        OrderJPA order2 = new OrderJPA(2L, 123L, 5, "TestApotheke2",
                LocalDate.parse("2020-02-05"), OrderStatus.RECEIVED);
        OrderJPA order3 = new OrderJPA(3L, 124L, 5, "TestApotheke1",
                LocalDate.parse("2020-03-05"), OrderStatus.RECEIVED);
        OrderJPA order4 = new OrderJPA(4L, 124L, 5, "TestApotheke3",
                LocalDate.parse("2020-05-05"), OrderStatus.RECEIVED);
        OrderJPA order5 = new OrderJPA(5L, 125L, 5, "TestApotheke4",
                LocalDate.parse("2020-05-05"), OrderStatus.RECEIVED);

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        orderRepository.save(order4);
        orderRepository.save(order5);
    }

    @AfterEach
    void clearDatabase(){
        orderRepository.deleteAll();
    }

    @Test
    void findByDate() {
        LocalDate date = LocalDate.parse("2020-05-05");
        List<OrderJPA> orders = orderRepository.findByDate(date);
        assertEquals(2, orders.size());
    }

    @Test
    void findByStoreName() {
        List<OrderJPA> orders = orderRepository.findByStoreName("TestApotheke1");
        assertEquals(2, orders.size());
    }

    @Test
    void findByDateAndStoreName() {
        LocalDate date = LocalDate.parse("2020-05-05");
        List<OrderJPA> orders = orderRepository.findByDateAndStoreName(date, "TestApotheke4");
        assertEquals(1, orders.size());
    }
}