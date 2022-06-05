package com.spruhs.apothek.presentation.controller;

import com.spruhs.apothek.business.medication.Medication;
import com.spruhs.apothek.business.medication.MedicationNotfound;
import com.spruhs.apothek.business.medication.MedicationService;
import com.spruhs.apothek.business.medication.NotEnoughMedicationInStock;
import com.spruhs.apothek.business.order.Order;
import com.spruhs.apothek.business.order.OrderService;
import com.spruhs.apothek.business.order.RequestOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */

@RestController
@RequestMapping("/pharmacy/store")
public class PharmaciesStoreController {

    private final MedicationService medicationService;
    private final OrderService orderService;

    public PharmaciesStoreController(MedicationService medicationService, OrderService orderService) {
        this.medicationService = medicationService;
        this.orderService = orderService;
    }

    /**
     * Sends an Order Object with the order of a Medication.
     *
     * @param order an Object of the type Order.
     * @return HttpStatus.OK if success,
     *      HttpStatus.NOT_FOUND if Medication not found,
     *      HttpStatus.BAD_REQUEST if order is a negative number or
     *      HttpStatus.SERVICE_UNAVAILABLE if the available amount of the Medication is not enough
     */
    @PutMapping("/order")
    public ResponseEntity<?> orderMedications(@Valid @RequestBody RequestOrder order) {
        orderService.save(order);
        try {
            medicationService.order(order);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MedicationNotfound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotEnoughMedicationInStock e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
