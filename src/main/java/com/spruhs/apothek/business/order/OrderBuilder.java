package com.spruhs.apothek.business.order;

import org.springframework.stereotype.Service;

@Service
public class OrderBuilder {


    public Order orderMapper(RequestOrder requestOrder) {
        Order result = new Order();
        result.setStoreName(requestOrder.getStoreName());
        result.setPharmaCentralNumber(requestOrder.getPharmaCentralNumber());
        result.setNumber(requestOrder.getNumber());
        return result;
    }
}
