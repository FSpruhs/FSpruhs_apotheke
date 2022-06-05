package com.spruhs.apothek.persistence;

import com.spruhs.apothek.business.order.Order;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */

public interface OrderRepository extends CrudRepository<Order, Long> {
    /**
     *
     * @param date date to find orders
     * @return List of orders of the date
     */
    List<Order> findByDate(LocalDate date);

    /**
     *
     * @param storeName name of the store to find orders
     * @return list of orders from the store
     */
    List<Order> findByStoreName(String storeName);

    /**
     *
     * @param date date to find orders
     * @param storeName name of the store to find orders
     * @return list of orders from the store on the date
     */
    List<Order> findByDateAndStoreName(LocalDate date, String storeName);

}
