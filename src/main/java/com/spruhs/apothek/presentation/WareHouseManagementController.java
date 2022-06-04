package com.spruhs.apothek.presentation;


import com.spruhs.apothek.business.Medication;
import com.spruhs.apothek.business.MedicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */

@RestController
@RequestMapping("/pharmacy/warehouse")
public class WareHouseManagementController {

    private final MedicationService medicationService;

    public WareHouseManagementController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    /**
     * Invokes the method of the model to add a new Medication to the Database.
     *
     * @param medication JSON Object of a Medication with valid check
     * @return ResponseEntity with status code
     */

    @PostMapping("")
    public ResponseEntity<?> createMedication(@Valid @RequestBody Medication medication) {
        return medicationService.createMedication(medication);
    }

    /**
     * Invokes the method of the model to delete a Medication.
     * Invokes method with id if only ID parameter is present.
     * Invokes method with name if only name parameter is present.
     *
     * @param id
     * @param name
     * @return if both or none parameter is permitted HttpStatus.BAD_REQUEST,
     *      else ResponseEntity with status code
     */

    @DeleteMapping("")
    public ResponseEntity<?> getMedication(@RequestParam(required = false) Long id,
                                           @RequestParam(required = false) String name) {
        if ((id == null && name == null) || (id != null && name != null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (name != null) {
            return medicationService.deleteMedicationByName(name);
        } else {
            return medicationService.deleteMedicationById(id);
        }
    }

    /**
     * Invokes the method of the model to fill up the quantity of the Medication
     *
     * @param id pharmaCentralNumber of the Medication to fill up
     * @param number amount to fill up
     * @return ResponseEntity with status code
     */

    @PutMapping("")
    public ResponseEntity<?> updateMedications(@RequestParam Long id, @RequestParam int number) {
        return medicationService.updateMedication(id, number);
    }

}
