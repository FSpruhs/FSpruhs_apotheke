package com.spruhs.apothek.presentation;

import com.spruhs.apothek.business.MedicationService;
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
     * Invokes the model function to find all Medications
     *
     * @return ResponseEntity with status code and result
     */
    @GetMapping("medicationsStock")
    public ResponseEntity<?> getAllMedications() {
        return medicationService.getAllMedications();
    }

    /**
     * Invokes the model function to find a Medication by name or by pharmaCentralNumber
     *
     * @param id pharmaCentralNumber of the Medication to find
     * @param name name of the Medication to find
     * @return ResponseEntity with status code and result
     */
    @GetMapping("medication")
    public ResponseEntity<?> getMedication(@RequestParam(required = false) Long id,
                                           @RequestParam(required = false) String name) {
        if ((id == null && name == null) || (id != null && name != null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (name != null) {
            return medicationService.getMedicationByName(name);
        } else {
            return medicationService.getMedicationById(id);
        }
    }

}
