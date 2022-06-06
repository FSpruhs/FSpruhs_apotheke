package com.spruhs.apothek.persistence;

import com.spruhs.apothek.business.medication.Medication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MedicationRepositoryTest {

    @Autowired
    MedicationRepository medicationRepository;

    Medication medication1 = new Medication(4324188,
            "Aspirin",
            "DL-Lysinmono(acetylsalicylat)-Glycin",
            "Bayer AG",
            842);

    @Test
    void findByNameIs() {
        medicationRepository.save(medication1);
        List<Medication> medication = medicationRepository.findByNameIs("Aspirin");
        assertEquals("Aspirin", medication.get(0).getName());
    }
}