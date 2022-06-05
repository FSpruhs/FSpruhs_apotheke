package com.spruhs.apothek.business.order;

import com.spruhs.apothek.business.medication.Medication;
import com.spruhs.apothek.business.medication.MedicationDTO;
import com.spruhs.apothek.persistence.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    OrderBuilder orderBuilder;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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
     * @param date date to find Orders.
     * @return Iterable wit all Orders from the date.
     * @throws OrderNotFound if no Orders found.
     */
    public Iterable<Order> getOrderByDate(LocalDate date) throws OrderNotFound {
        List<Order> listMedication = orderRepository.findByDate(date);
        if (!listMedication.isEmpty()) {
            return listMedication;
        } else {
            throw new OrderNotFound();
        }
    }

    /**
     * Outputs Orders in the Database by the store name.
     *
     * @param storeName storeName to find Orders.
     * @return Iterable wit all Orders from the store.
     * @throws OrderNotFound if no Orders found.
     */
    public Iterable<Order> getOrderByStoreName(String storeName) throws OrderNotFound {
        List<Order> listMedication = orderRepository.findByStoreName(storeName);
        if (!listMedication.isEmpty()) {
            return listMedication;
        } else {
            throw new OrderNotFound();
        }
    }

    /**
     * Outputs Orders in the Database by the store name and date.
     *
     * @param storeName storeName to find Orders.
     * @param date date to find Orders.
     * @return Iterable wit all Orders from the store and date.
     * @throws OrderNotFound if no Orders found.
     */
    public Iterable<Order> getOrderByDatStore(LocalDate date, String storeName) throws OrderNotFound {
        List<Order> listMedication = orderRepository.findByDateAndStoreName(date, storeName);
        if (!listMedication.isEmpty()) {
            return listMedication;
        } else {
            throw new OrderNotFound();
        }
    }

    /**
     * Deletes an order in the Database by id.
     *
     * @param id id to delete.
     * @throws OrderNotFound if order not found.
     */
    public void deleteById(Long id) throws OrderNotFound {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            orderRepository.deleteById(id);
        } else {
            throw new OrderNotFound();
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
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            optionalOrder.get().setOrderStatus(orderStatus);
            orderRepository.save(optionalOrder.get());
        } else {
            throw new OrderNotFound();
        }
    }

    /**
     * Outputs all Orders in the Database.
     *
     * @return Iterable with all Orders in the Database.
     */
    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
