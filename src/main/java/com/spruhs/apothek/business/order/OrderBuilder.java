package com.spruhs.apothek.business.order;

import org.springframework.stereotype.Service;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
@Service
public class OrderBuilder {


    public OrderJPA orderMapper(RequestOrder requestOrder) {
        OrderJPA result = new OrderJPA();
        result.setStoreName(requestOrder.getStoreName());
        result.setPharmaCentralNumber(requestOrder.getPharmaCentralNumber());
        result.setNumber(requestOrder.getNumber());
        return result;
    }
}
