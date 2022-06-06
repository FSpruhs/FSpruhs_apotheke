package com.spruhs.apothek.persistence;

import com.spruhs.apothek.business.order.OrderJPA;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
@Repository
public interface OrderRepository extends CrudRepository<OrderJPA, Long> {
    /**
     *
     * @param date date to find orders
     * @return List of orders of the date
     */
    List<OrderJPA> findByDate(LocalDate date);

    /**
     *
     * @param storeName name of the store to find orders
     * @return list of orders from the store
     */
    List<OrderJPA> findByStoreName(String storeName);

    /**
     *
     * @param date date to find orders
     * @param storeName name of the store to find orders
     * @return list of orders from the store on the date
     */
    List<OrderJPA> findByDateAndStoreName(LocalDate date, String storeName);

}
