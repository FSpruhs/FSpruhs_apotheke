package com.spruhs.apothek.business.medication;


import com.spruhs.apothek.business.order.RequestOrder;
import com.spruhs.apothek.persistence.MedicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final ModelMapper modelMapper;

    public final MedicationRepository medicationRepository;

    @Autowired
    public MedicationService(ModelMapper modelMapper, MedicationRepository medicationRepository) {
        this.modelMapper = modelMapper;
        this.medicationRepository = medicationRepository;
    }

    /**
     * Adds a Medication Entity to the Database
     *
     * @param medication Medication Entity
     */
    public void createMedication(Medication medication) {
        medicationRepository.save(medication);
    }

    /**
     * Outputs all Medications from the Database.
     *
     * @return an iterable of all Medications from the Database.
     */
    public Iterable<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    /**
     * Finds a Medication by id form the Database.
     *
     * @param id id from the Medication to find.
     * @return Medication Object with the id.
     * @throws MedicationNotfound if Medication not found.
     */
    public Medication getMedicationById(Long id) throws MedicationNotfound {
        Optional<Medication> optionalMedication = medicationRepository.findById(id);
        if (optionalMedication.isPresent()) {
            return  optionalMedication.get();
        } else {
            throw new MedicationNotfound("No such Medication found!");
        }
    }

    /**
     * Finds a Medication by name form the Database.
     *
     * @param name name from the Medication to find.
     * @return Medication List with the name.
     * @throws MedicationNotfound if Medication not found.
     */
    public List<MedicationDTO> getMedicationByName(String name) throws MedicationNotfound {
        List<Medication> listMedication = medicationRepository.findByNameIs(name);
        if (!listMedication.isEmpty()) {
            return Arrays.asList(modelMapper.map(listMedication, MedicationDTO[].class));
        } else {
            throw new MedicationNotfound("No such Medication found!");
        }
    }

    /**
     * Order a Medication with an Order Object. Reduce the quantity of the Medication available by the ordered amount.
     *
     * @param order an Object of type Order.
     * @throws MedicationNotfound if the Medication is not found in the Database
     * @throws NotEnoughMedicationInStock if the order amount of the medication is higher than the amount in the stock.
     * @throws IllegalArgumentException if order number is negative
     */
    public void order(RequestOrder order) throws MedicationNotfound, NotEnoughMedicationInStock {
        if (order.getNumber() < 0) {
            throw new IllegalArgumentException("Can not order a negative number!");
        }
        Optional<Medication> optionalMedication = medicationRepository.findById(order.getPharmaCentralNumber());
        if (optionalMedication.isEmpty()) {
            throw new MedicationNotfound("No such Medication found!");
        } else if (optionalMedication.get().getAvailable() > order.getNumber()) {
            optionalMedication.get().setAvailable(optionalMedication.get().getAvailable() - order.getNumber());
            medicationRepository.save(optionalMedication.get());
        } else {
            throw new NotEnoughMedicationInStock("Not enough medicine in the Stock!");
        }
    }

    /**
     * Deletes all the Medication by name from the Database.
     *
     * @param name name of the Medication to delete.
     * @throws MedicationNotfound if Medication not found.
     */
    public void deleteMedicationByName(String name) throws MedicationNotfound {
        List<Medication> medicationList = medicationRepository.findByNameIs(name);
        if (medicationList.isEmpty()) {
            throw new MedicationNotfound("No such Medication found!");
        } else {
            medicationRepository.deleteAll(medicationList);
        }
    }

    /**
     * Deletes the Medication by pharmaCentralNumber from the Database.
     *
     * @param id  pharmaCentralNumber of the Medication to delete.
     * @throws MedicationNotfound if Medication not found.
     */
    public void deleteMedicationById(Long id) throws MedicationNotfound {
        Optional<Medication> optionalMedication = medicationRepository.findById(id);
        if (optionalMedication.isPresent()) {
            medicationRepository.deleteById(id);
        } else {
            throw new MedicationNotfound("No such Medication found!");
        }
    }

    /**
     * Increases the available quantity of a Medication in the Database.
     *
     * @param id pharmaCentralNumber of the Medication to increase.
     * @param number number of increasing Medication.
     * @throws MedicationNotfound if Medication not found.
     */
    public void updateMedication(Long id, int number) throws MedicationNotfound {
        Optional<Medication> optionalMedication = medicationRepository.findById(id);
        if (optionalMedication.isPresent()) {
            optionalMedication.get().setAvailable(optionalMedication.get().getAvailable() + number);
            medicationRepository.save(optionalMedication.get());
        } else {
            throw new MedicationNotfound("No such Medication found!");
        }
    }
}
