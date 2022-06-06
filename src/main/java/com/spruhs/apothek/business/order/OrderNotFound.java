package com.spruhs.apothek.business.order;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
public class OrderNotFound extends Exception{

    public OrderNotFound(String text) {
        super(text);
    }
}
