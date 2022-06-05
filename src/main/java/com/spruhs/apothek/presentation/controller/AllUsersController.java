package com.spruhs.apothek.presentation.controller;

import com.spruhs.apothek.business.medication.MedicationNotfound;
import com.spruhs.apothek.business.medication.MedicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */

@RestController
@RequestMapping("/pharmacy/allUsers")
public class AllUsersController {

    private final MedicationService medicationService;

    public AllUsersController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    /**
     * Outputs all Medications in the Database.
     *
     * @return All Medication from the Database nd HttpStatus Ok,
     *
     */
    @GetMapping("medication/stock")
    public ResponseEntity<?> getAllMedications() {
        return new ResponseEntity<>(medicationService.getAllMedications(), HttpStatus.OK);
    }

    /**
     * Finds a Medication by Name from the Database
     *
     * @param name name of the Medication
     * @return List of Medication with the name and HttpStatus Ok,
     *      HttpStatus.NOT_Found if no Medication found
     */
    @GetMapping("medication/name")
    public ResponseEntity<?> getMedicationByName(@RequestParam String name) {
        try {
            return new ResponseEntity<>(medicationService.getMedicationByName(name), HttpStatus.OK);
        } catch (MedicationNotfound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Finds a Medication by id from the Database
     *
     * @param id name of the Medication
     * @return Dto Object of Medication with the name and HttpStatus Ok,
     *      HttpStatus.NOT_Found if no Medication found
     */
    @GetMapping("medication/id")
    public ResponseEntity<?> getMedicationById(@RequestParam long id) {
        try {
            return new ResponseEntity<>(medicationService.getMedicationById(id), HttpStatus.OK);
        } catch (MedicationNotfound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
