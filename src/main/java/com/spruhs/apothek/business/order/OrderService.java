package com.spruhs.apothek.business.order;

import com.spruhs.apothek.persistence.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
@Service
public class OrderService {

    private final String ERROR_MESSAGE_ORDER_NOT_FOUND = "No such Order found!";

    private final OrderRepository orderRepository;

    private final OrderBuilder orderBuilder;


    public OrderService(OrderRepository orderRepository, OrderBuilder orderBuilder) {
        this.orderRepository = orderRepository;
        this.orderBuilder = orderBuilder;
    }

    /**
     * Saves Order Object in the Database.
     *
     * @param order saves the order in the database
     */
    public void save(RequestOrder order) {
        orderRepository.save(orderBuilder.orderMapper(order));
    }

    /**
     * Outputs Orders in the Database by date.
     *
     * @param date date to find Orders. Format YYYY-MM-DD
     * @return Iterable wit all Orders from the date.
     * @throws OrderNotFound if no Orders found.
     */
    public Iterable<OrderJPA> getOrderByDate(LocalDate date) throws OrderNotFound {
        List<OrderJPA> listMedication = orderRepository.findByDate(date);
        if (!listMedication.isEmpty()) {
            return listMedication;
        } else {
            throw new OrderNotFound(ERROR_MESSAGE_ORDER_NOT_FOUND);
        }
    }

    /**
     * Outputs Orders in the Database by the store name.
     *
     * @param storeName storeName to find Orders.
     * @return Iterable wit all Orders from the store.
     * @throws OrderNotFound if no Orders found.
     */
    public Iterable<OrderJPA> getOrderByStoreName(String storeName) throws OrderNotFound {
        List<OrderJPA> listMedication = orderRepository.findByStoreName(storeName);
        if (!listMedication.isEmpty()) {
            return listMedication;
        } else {
            throw new OrderNotFound(ERROR_MESSAGE_ORDER_NOT_FOUND);
        }
    }

    /**
     * Outputs Orders in the Database by the store name and date.
     *
     * @param storeName storeName to find Orders.
     * @param date date to find Orders. Format YYYY-MM-DD
     * @return Iterable wit all Orders from the store and date.
     * @throws OrderNotFound if no Orders found.
     */
    public Iterable<OrderJPA> getOrderByDateAndStore(LocalDate date, String storeName) throws OrderNotFound {
        List<OrderJPA> listMedication = orderRepository.findByDateAndStoreName(date, storeName);
        if (!listMedication.isEmpty()) {
            return listMedication;
        } else {
            throw new OrderNotFound(ERROR_MESSAGE_ORDER_NOT_FOUND);
        }
    }

    /**
     * Deletes an order in the Database by id.
     *
     * @param id id to delete.
     * @throws OrderNotFound if order not found.
     */
    public void deleteById(Long id) throws OrderNotFound {
        Optional<OrderJPA> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            orderRepository.deleteById(id);
        } else {
            throw new OrderNotFound(ERROR_MESSAGE_ORDER_NOT_FOUND);
        }
    }

    /**
     * Updates the status of an order.
     *
     * @param id id of the order to update.
     * @param orderStatus new status of the order.
     * @throws OrderNotFound if order not found.
     */
    public void updateStatus(Long id, OrderStatus orderStatus) throws OrderNotFound {
        Optional<OrderJPA> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            optionalOrder.get().setOrderStatus(orderStatus);
            orderRepository.save(optionalOrder.get());
        } else {
            throw new OrderNotFound(ERROR_MESSAGE_ORDER_NOT_FOUND);
        }
    }

    /**
     * Outputs all Orders in the Database.
     *
     * @return Iterable with all Orders in the Database.
     */
    public Iterable<OrderJPA> getAllOrders() {
        return orderRepository.findAll();
    }
}
