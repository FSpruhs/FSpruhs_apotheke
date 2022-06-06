package com.spruhs.apothek.presentation.controller;


import com.spruhs.apothek.business.medication.Medication;
import com.spruhs.apothek.business.medication.MedicationNotfound;
import com.spruhs.apothek.business.medication.MedicationService;
import com.spruhs.apothek.business.order.OrderJPA;
import com.spruhs.apothek.business.order.OrderNotFound;
import com.spruhs.apothek.business.order.OrderService;
import com.spruhs.apothek.business.order.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
@RestController
@RequestMapping("/pharmacy/warehouse")
public class WareHouseManagementController {

    private final MedicationService medicationService;
    private final OrderService orderService;

    public WareHouseManagementController(MedicationService medicationService, OrderService orderService) {
        this.medicationService = medicationService;
        this.orderService = orderService;
    }

    /**
     * Invokes the method of the model to add a new Medication to the Database.
     *
     * @param medication JSON Object of a Medication with valid check
     * @return HttpStatus Ok
     */
    @PostMapping("")
    public ResponseEntity<?> createMedication(@Valid @RequestBody Medication medication) {
        medicationService.createMedication(medication);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Invokes the method of the model to delete a Medication.
     * Invokes method with id if only ID parameter is present.
     * Invokes method with name if only name parameter is present.
     *
     * @param id pharmaCentralNumber of the Medication
     * @param name name of the Medication
     * @return if both or none parameter is permitted HttpStatus.BAD_REQUEST,
     *      if Medication not found return HttpStatus not found,
     *      else ResponseEntity with status code
     */
    @DeleteMapping("")
    public ResponseEntity<?> deleteMedication(@RequestParam(required = false) Long id,
                                              @RequestParam(required = false) String name) {
        if ((id == null && name == null) || (id != null && name != null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            if (name != null) {
                medicationService.deleteMedicationByName(name);
            } else {
                medicationService.deleteMedicationById(id);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MedicationNotfound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Invokes the method of the model to fill up the quantity of the Medication
     *
     * @param id pharmaCentralNumber of the Medication to fill up
     * @param number amount to fill up
     * @return HttpStatus not found if Medication not found,
     *      else HttpStatus ok.
     */
    @PutMapping("")
    public ResponseEntity<?> updateMedications(@RequestParam Long id, @RequestParam int number) {
        try {
            medicationService.updateMedication(id, number);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MedicationNotfound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     *Outputs all Orders in the Database.
     *
     * @return All orders in the Database and HttpStatus ok.
     */
    @GetMapping("/order/all")
    public ResponseEntity<?> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);

    }

    /**
     * Displays all orders by the given date, Store Name or both form the Database.
     *
     * @param date date to show order
     * @param storeName Store to show order
     * @return Outputs all Orders by date, store name or date and store name,
     *      returns HttpStatus bad request if both requestparm are null,
     *      returns HttpStatus not found if no orders found.
     */
    @GetMapping("order")
    public ResponseEntity<?> getOrder(@RequestParam(required = false) String date,
                                      @RequestParam(required = false) String storeName) {


        if ((date == null && storeName == null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Iterable<OrderJPA> orders;
        try {
            if (date != null && storeName != null) {
                orders = orderService.getOrderByDateAndStore(stringToLocaleDate(date), storeName);
            } else if (storeName != null) {
                orders = orderService.getOrderByStoreName(storeName);
            } else {
                orders = orderService.getOrderByDate(stringToLocaleDate(date));
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (OrderNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an order from the Database by id.
     *
     * @param id id of the order to delete.
     * @return HttpStatus not found order not found,
     *      else HttpStatus ok.
     */
    @DeleteMapping("order")
    public ResponseEntity<?> deleteOrder(@RequestParam Long id) {
        try {
            orderService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OrderNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates the order status of an order in the Database
     *
     * @param id the id of the order to update
     * @param orderStatus the new order status of the order
     * @return ResponseEntity with status code
     */
    @PutMapping("order")
    public ResponseEntity<?> updateOrderStatus(@RequestParam Long id, @RequestParam OrderStatus orderStatus) {
        try {
            orderService.updateStatus(id, orderStatus);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(OrderNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Utility Function to transform a string to a Locale Date Object.
     *
     * @param date string to transform.
     * @return Local Date Object.
     */
    private LocalDate stringToLocaleDate(String date) {
        return LocalDate.parse(date);
    }

}
