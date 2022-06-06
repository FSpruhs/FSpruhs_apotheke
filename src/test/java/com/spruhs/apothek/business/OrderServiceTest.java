package com.spruhs.apothek.business;

import com.spruhs.apothek.business.order.*;
import com.spruhs.apothek.persistence.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderBuilder orderBuilder;

    @InjectMocks
    OrderService orderService;

    RequestOrder order1 = new RequestOrder(4324188L, 1, "ApothekeTest");
    OrderJPA orderJPA1 = new OrderJPA(1L, 4324188L, 1, "TestApotheke",
            LocalDate.now(), OrderStatus.RECEIVED);
    OrderJPA orderJPA2 = new OrderJPA(2L, 4356188, 1, "TestApotheke1",
            LocalDate.now(), OrderStatus.RECEIVED);
    OrderJPA orderJPA3 = new OrderJPA(3L, 4324188L, 1, "TestApotheke2",
            LocalDate.now(), OrderStatus.RECEIVED);

    @Test
    void save() {
        orderService.save(order1);
        verify(orderRepository, times(1)).save(orderBuilder.orderMapper(order1));
    }

    @Test
    void getOrderByDateSuccess() throws OrderNotFound {
        List<OrderJPA> orderJPAS = new ArrayList<>();
        orderJPAS.add(orderJPA1);
        orderJPAS.add(orderJPA2);
        orderJPAS.add(orderJPA3);

        when(orderRepository.findByDate(LocalDate.now())).thenReturn(orderJPAS);

        List<OrderJPA> orderJPAList = (List<OrderJPA>) orderService.getOrderByDate(LocalDate.now());
        assertEquals(3, orderJPAList.size());

        verify(orderRepository, times(1)).findByDate(LocalDate.now());
    }

    @Test
    void getOrderByDateFailure() {
        List<OrderJPA> orderJPAS = new ArrayList<>();
        orderJPAS.add(orderJPA1);
        orderJPAS.add(orderJPA2);
        orderJPAS.add(orderJPA3);

        when(orderRepository.findByDate(any(LocalDate.class))).thenReturn(orderJPAS);
        try {
            orderService.getOrderByDate(LocalDate.parse("2020-01-01"));
        } catch (OrderNotFound e) {
            assertEquals(e.getMessage(), "No such order Found!");
        }

        verify(orderRepository, times(0)).findByDate(LocalDate.now());
    }

    @Test
    void getOrderByNameSuccess() throws OrderNotFound {
        List<OrderJPA> orderJPAS = new ArrayList<>();
        orderJPAS.add(orderJPA1);


        when(orderRepository.findByStoreName(anyString())).thenReturn(orderJPAS);

        List<OrderJPA> orderJPAList = (List<OrderJPA>) orderService.getOrderByStoreName("TestApotheke");
        assertEquals(1, orderJPAList.size());

        verify(orderRepository, times(1)).findByStoreName("TestApotheke");
    }

    @Test
    void getOrderByNameFailure() {
        List<OrderJPA> orderJPAS = new ArrayList<>();

        when(orderRepository.findByDate(any(LocalDate.class))).thenReturn(orderJPAS);
        try {
            orderService.getOrderByDate(LocalDate.parse("2020-01-01"));
        } catch (OrderNotFound e) {
            assertEquals(e.getMessage(), "No such order Found!");
        }

        verify(orderRepository, times(0)).findByDate(LocalDate.now());
    }

    @Test
    void getOrderByNameAndDateSuccess() throws OrderNotFound {
        List<OrderJPA> orderJPAS = new ArrayList<>();
        orderJPAS.add(orderJPA1);
        orderJPAS.add(orderJPA2);


        when(orderRepository.findByDateAndStoreName(any(LocalDate.class), eq("TestApotheke"))).thenReturn(orderJPAS);

        List<OrderJPA> orderJPAList = (List<OrderJPA>) orderService.getOrderByDateAndStore(LocalDate.now(), "TestApotheke");
        assertEquals(2, orderJPAList.size());

        verify(orderRepository, times(1)).findByDateAndStoreName(LocalDate.now(), "TestApotheke");
    }

    @Test
    void getOrderByNameAndDateFailure() {
        List<OrderJPA> orderJPAS = new ArrayList<>();

        when(orderRepository.findByDateAndStoreName(any(LocalDate.class), eq("TestApotheke"))).thenReturn(orderJPAS);
        try {
            orderService.getOrderByDateAndStore(LocalDate.parse("2020-01-01"), "TestApotheke");
        } catch (OrderNotFound e) {
            assertEquals(e.getMessage(), "No such order Found!");
        }

        verify(orderRepository, times(0)).findByDateAndStoreName(LocalDate.now(), "TestApotheke");
    }

    @Test
    void deleteByIdSuccess() throws OrderNotFound {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(orderJPA1));
        orderService.deleteById(orderJPA1.getId());
        verify(orderRepository, times(1)).deleteById(orderJPA1.getId());
    }

    @Test
    void deleteByIdFailure() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(orderJPA2));
        try {
            orderService.deleteById(orderJPA1.getId());
        } catch (OrderNotFound e) {
            assertEquals(e.getMessage(), "No such Order found!");
        }

        verify(orderRepository, times(1)).findById(orderJPA1.getId());
        verify(orderRepository, times(1)).deleteById(orderJPA1.getId());
    }

    @Test
    void getAllOrder() {
        List<OrderJPA> orderJPAS = new ArrayList<>();
        orderJPAS.add(orderJPA1);
        orderJPAS.add(orderJPA2);
        orderJPAS.add(orderJPA3);

        when(orderRepository.findAll()).thenReturn(orderJPAS);

        List<OrderJPA> orderJPAList = (List<OrderJPA>) orderService.getAllOrders();
        assertEquals(3, orderJPAList.size());

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void updateOrderStatusSuccess() throws OrderNotFound {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(orderJPA1));
        orderService.updateStatus(1L, OrderStatus.DONE);
        Optional<OrderJPA> orderJPA = orderRepository.findById(orderJPA1.getId());
        assertEquals(OrderStatus.DONE, orderJPA.get().getOrderStatus());

        verify(orderRepository, times(2)).findById(orderJPA1.getId());
        verify(orderRepository, times(1)).save(orderJPA1);
    }

    @Test
    void updateOrderStatusFailure() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(orderJPA2));
        try {
            orderService.updateStatus(orderJPA1.getId(), OrderStatus.DONE);
        } catch (OrderNotFound e) {
            assertEquals(e.getMessage(), "No such Order found!");
        }

        verify(orderRepository, times(1)).findById(orderJPA1.getId());
        verify(orderRepository, times(0)).save(orderJPA1);
    }

}
