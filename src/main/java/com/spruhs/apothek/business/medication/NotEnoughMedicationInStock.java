package com.spruhs.apothek.business.medication;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
public class NotEnoughMedicationInStock extends Exception{

    public NotEnoughMedicationInStock(String text) {
        super(text);
    }
}
