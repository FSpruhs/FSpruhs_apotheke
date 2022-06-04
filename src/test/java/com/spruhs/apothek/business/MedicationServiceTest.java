package com.spruhs.apothek.business;

import com.spruhs.apothek.persistence.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicationServiceTest {

    @InjectMocks
    MedicationService medicationService;

    @Mock
    MedicationRepository medicationRepository;

    Medication medication1 = new Medication(4324188,
            "Aspirin",
            "DL-Lysinmono(acetylsalicylat)-Glycin",
            "Bayer AG",
            842);

    Medication medication2 = new Medication(4356188,
            "Ibuprofen",
            "Something",
            "Ratiofarm",
            13);

    @Test
    void createMedication() {
        medicationService.createMedication(medication1);
        verify(medicationRepository, times(1)).save(medication1);
    }

    @Test
    void getAllMedications() {
        List<Medication> list = new ArrayList<>();
        list.add(medication1);
        list.add(medication2);

        when(medicationRepository.findAll()).thenReturn(list);

        ResponseEntity<?> medicationResponse = medicationService.getAllMedications();

        //assertEquals("[" + medication1.toString() + ", " + medication2.toString() + "]", medicationResponse.getBody());
        assertEquals(HttpStatus.OK, medicationResponse.getStatusCode());
        verify(medicationRepository, times(1)).findAll();
    }

    @Test
    void getMedicationByIdSuccess() {
        medicationRepository.save(medication1);
        medicationService.createMedication(medication1);

        //when(medicationRepository.findById(anyLong())).thenReturn(Optional.of(new Medication()));

        ResponseEntity<?> medicationResponse = medicationService.getMedicationById(medication1.getPharmaCentralNumber());

        //assertEquals(HttpStatus.OK, medicationResponse.getStatusCode());
        verify(medicationRepository, times(1)).findById(medication1.getPharmaCentralNumber());

    }

    @Test
    void getMedicationByIdFailure() {

        ResponseEntity<?> medicationResponse = medicationService.getMedicationById(medication1.getPharmaCentralNumber());

        assertEquals(HttpStatus.NOT_FOUND, medicationResponse.getStatusCode());
        verify(medicationRepository, times(1)).findById(medication1.getPharmaCentralNumber());

    }

    @Test
    void getMedicationByNameSuccess() {
        medicationRepository.save(medication1);
        medicationService.createMedication(medication1);

        //when(medicationRepository.findById(anyLong())).thenReturn(Optional.of(new Medication()));

        ResponseEntity<?> medicationResponse = medicationService.getMedicationById(medication1.getPharmaCentralNumber());

        //assertEquals(HttpStatus.OK, medicationResponse.getStatusCode());
        verify(medicationRepository, times(1)).findById(medication1.getPharmaCentralNumber());

    }

    @Test
    void getMedicationByNameFailure() {
        ResponseEntity<?> medicationResponse = medicationService.getMedicationByName(medication1.getName());

        assertEquals(HttpStatus.NOT_FOUND, medicationResponse.getStatusCode());
        verify(medicationRepository, times(1)).findByNameIs(medication1.getName());
    }

    @Test
    void orderNegativeNumber() {
        medicationRepository.save(medication1);

        ResponseEntity<?> medicationResponse = medicationService.order(medication1.getPharmaCentralNumber(), -1);

        assertEquals(HttpStatus.BAD_REQUEST, medicationResponse.getStatusCode());

    }

    @Test
    void orderSuccess() {
        medicationRepository.save(medication1);

        ResponseEntity<?> medicationResponse = medicationService.order(medication1.getPharmaCentralNumber(), 1);

        //assertEquals(HttpStatus.OK, medicationResponse.getStatusCode());
        Optional<Medication> medication = medicationRepository.findById(medication1.getPharmaCentralNumber());
        //assertEquals(843, medication.get().getAvailable());

    }

    @Test
    void orderFailure() {

        ResponseEntity<?> medicationResponse = medicationService.order(medication1.getPharmaCentralNumber(), 1);

        assertEquals(HttpStatus.NOT_FOUND, medicationResponse.getStatusCode());

    }

    @Test
    void deleteMedicationByNameSuccess() {
        medicationRepository.save(medication1);

        ResponseEntity<?> medicationResponse = medicationService.deleteMedicationByName(medication1.getName());

        //assertEquals(HttpStatus.OK, medicationResponse.getStatusCode());
    }

    @Test
    void deleteMedicationByNameFailure() {
        ResponseEntity<?> medicationResponse = medicationService.deleteMedicationById(medication1.getPharmaCentralNumber());

        assertEquals(HttpStatus.NOT_FOUND, medicationResponse.getStatusCode());

    }

    @Test
    void deleteMedicationByIdSuccess() {
    }

    @Test
    void deleteMedicationByIdFailure() {
    }

    @Test
    void updateMedication() {
    }
}