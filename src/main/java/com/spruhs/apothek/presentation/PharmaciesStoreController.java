package com.spruhs.apothek.presentation;

import com.spruhs.apothek.business.MedicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */

@RestController
@RequestMapping("/pharmacy/pharmaciesStore")
public class PharmaciesStoreController {

    private final MedicationService medicationService;

    public PharmaciesStoreController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    /**
     * Invokes the model function to set an order of a Medication
     *
     * @param id pharmaCentralNumber of the ordered Medication
     * @param number the amount of ordered Medication
     * @return ResponseEntity wit status code
     */

    @PutMapping("order")
    public ResponseEntity<?> orderMedications(@RequestParam Long id, @RequestParam int number) {
        return medicationService.order(id, number);
    }

}
