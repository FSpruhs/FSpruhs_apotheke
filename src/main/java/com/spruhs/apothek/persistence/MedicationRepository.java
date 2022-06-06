package com.spruhs.apothek.persistence;

import com.spruhs.apothek.business.medication.Medication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
@Repository
public interface MedicationRepository extends CrudRepository<Medication, Long> {

    /**
     * Returns a List with all Medications with the matching name in the Database
     *
     * @param name name of the Medication to find
     * @return List with Medications
     */
    List<Medication> findByNameIs(String name);

}
