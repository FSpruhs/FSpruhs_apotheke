package com.spruhs.apothek.business;


import com.spruhs.apothek.persistence.MedicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
@Service
public class MedicationService {

    @Autowired
    private ModelMapper modelMapper;

    public final MedicationRepository medicationRepository;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    /**
     * Adds a Medication Entity to the Database
     *
     * @param medication Medication Entity
     * @return HttpStatus.OK
     */

    public ResponseEntity<?> createMedication(Medication medication) {
        medicationRepository.save(medication);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Finds All Medications in the Database
     *
     * @return List with all Medications Objects, HttpStatus.OK
     */
    public ResponseEntity<?> getAllMedications() {
        return new ResponseEntity<>(medicationRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Founds Medication by pharmaCentralNumber
     *
     * @param id pharmaCentralNumber of a Medication
     * @return if Present Medication Object with HttpStatus.OK,
     *      or else HttpStatus.NOT_FOUND
     */

    public ResponseEntity<?> getMedicationById(Long id) {
        Optional<Medication> optionalMedication = medicationRepository.findById(id);
        if (optionalMedication.isPresent()) {
            return new ResponseEntity<>(modelMapper.map(optionalMedication.get(), MedicationDTO.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Founds Medication by name
     *
     * @param name name of the Medication
     * @return if Present Medication Object with HttpStatus.OK,
     *      or else HttpStatus.NOT_FOUND
     */

    public ResponseEntity<?> getMedicationByName(String name) {
        List<Medication> listMedication = medicationRepository.findByNameIs(name);
        if (!listMedication.isEmpty()) {
            List<MedicationDTO> medicationDTOS = Arrays.asList(modelMapper.map(listMedication, MedicationDTO[].class));
            return new ResponseEntity<>(medicationDTOS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Order a Medication. Reduce the quantity of the Medication available by the ordered amount.
     * The order can be placed by the Medication name or pharmaCentralNumber
     *
     * @param id pharmaCentralNumber of the ordered Medication
     * @param number name of the ordered Medication
     * @return HttpStatus.OK if success,
     *      HttpStatus.NOT_FOUND if Medication not found,
     *      HttpStatus.BAD_REQUEST if order is a negative number or
     *      HttpStatus.SERVICE_UNAVAILABLE if the available amount of the Medication is not enough
     */

    public ResponseEntity<?> order(Long id, int number) {
        if (number < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Medication> optionalMedication = medicationRepository.findById(id);
        if (optionalMedication.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (optionalMedication.get().getAvailable() > number) {
            optionalMedication.get().setAvailable(optionalMedication.get().getAvailable() - number);
            medicationRepository.save(optionalMedication.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * Deletes the Medication by name from the Database
     *
     * @param name name of the Medication to delete
     * @return HttpStatus.NOT_FOUND if Medication is not in the Database,
     *      HttpStatus.OK if success
     */

    public ResponseEntity<?> deleteMedicationByName(String name) {
        List<Medication> medicationList = medicationRepository.findByNameIs(name);
        if (medicationList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            medicationRepository.deleteAll(medicationList);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * Deletes the Medication by pharmaCentralNumber from the Database
     *
     * @param id pharmaCentralNumber of the Medication to delete
     * @return HttpStatus.NOT_FOUND if Medication is not in the Database,
     *      HttpStatus.OK if success
     */

    public ResponseEntity<?> deleteMedicationById(Long id) {
        Optional<Medication> optionalMedication = medicationRepository.findById(id);
        if (optionalMedication.isPresent()) {
            medicationRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Increases the available quantity of a Medication
     *
     * @param id pharmaCentralNumber of the Medication to increase
     * @param number number of increasing Medication
     * @return HttpStatus.NOT_FOUND if Medication is not in the Database,
     *      HttpStatus.OK if success
     */

    public ResponseEntity<?> updateMedication(Long id, int number) {
        Optional<Medication> optionalMedication = medicationRepository.findById(id);
        if (optionalMedication.isPresent()) {
            optionalMedication.get().setAvailable(optionalMedication.get().getAvailable() + number);
            medicationRepository.save(optionalMedication.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
