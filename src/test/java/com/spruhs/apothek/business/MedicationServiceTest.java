package com.spruhs.apothek.business;

import com.spruhs.apothek.business.medication.Medication;
import com.spruhs.apothek.business.medication.MedicationNotfound;
import com.spruhs.apothek.business.medication.MedicationService;
import com.spruhs.apothek.business.medication.NotEnoughMedicationInStock;
import com.spruhs.apothek.business.order.RequestOrder;
import com.spruhs.apothek.persistence.MedicationRepository;
import com.spruhs.apothek.persistence.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class MedicationServiceTest {

    @InjectMocks
    MedicationService medicationServiceMock;

    @Mock
    MedicationRepository medicationRepositoryMock;

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

    RequestOrder order1 = new RequestOrder(4324188L, 1, "ApothekeTest");
    RequestOrder order2 = new RequestOrder(4356188, 1000, "ApothekeTest1");
    RequestOrder order3 = new RequestOrder(4324188, -1, "ApothekeTest2");


    @Test
    void createMedication() {
        medicationServiceMock.createMedication(medication1);
        verify(medicationRepositoryMock, times(1)).save(medication1);
    }

    @Test
    void getAllMedications() {
        List<Medication> list = new ArrayList<>();
        list.add(medication1);
        list.add(medication2);

        when(medicationRepositoryMock.findAll()).thenReturn(list);

        Iterable<Medication> medicationIterable = medicationRepositoryMock.findAll();
        List<Medication> medicationList = new ArrayList<>();
        medicationIterable.forEach(medicationList::add);

        assertEquals(2, medicationList.size());
        verify(medicationRepositoryMock, times(1)).findAll();
    }

    @Test
    void getMedicationByIdSuccess() throws MedicationNotfound {

        when(medicationRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(medication1));
        Medication medication = medicationServiceMock.getMedicationById(4324188L);

        assertEquals("Aspirin", medication.getName());
        verify(medicationRepositoryMock, times(1)).findById(medication1.getPharmaCentralNumber());

    }

    @Test
    void getMedicationByIdFailure() throws MedicationNotfound {

        when(medicationRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(medication1));
        Medication medication = medicationServiceMock.getMedicationById(4324588L);

        assertEquals("Aspirin", medication.getName());
        verify(medicationRepositoryMock, times(0)).findById(medication1.getPharmaCentralNumber());
    }

    @Test
    void deleteByIdSuccess() throws MedicationNotfound {
        when(medicationRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(medication1));
        medicationServiceMock.deleteMedicationById(medication1.getPharmaCentralNumber());
        verify(medicationRepositoryMock, times(1)).deleteById(medication1.getPharmaCentralNumber());
    }

    @Test
    void deleteByIdFailure() {
        when(medicationRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(medication2));
        try {
            medicationServiceMock.deleteMedicationById(medication1.getPharmaCentralNumber());
        } catch (MedicationNotfound e) {
            assertEquals(e.getMessage(), "No such Medication found!");
        }

        verify(medicationRepositoryMock, times(1)).findById(medication1.getPharmaCentralNumber());
        verify(medicationRepositoryMock, times(1)).deleteById(medication1.getPharmaCentralNumber());
    }

    @Test
    void updateMedicationSuccess() throws MedicationNotfound {
        when(medicationRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(medication1));
        medicationServiceMock.updateMedication(4324188L, 1);
        Optional<Medication> medication = medicationRepositoryMock.findById(medication1.getPharmaCentralNumber());
        assertEquals(843, medication.get().getAvailable());

        verify(medicationRepositoryMock, times(2)).findById(medication1.getPharmaCentralNumber());
        verify(medicationRepositoryMock, times(1)).save(medication1);
    }

    @Test
    void updateMedicationFailure() {
        when(medicationRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(medication2));
        try {
            medicationServiceMock.updateMedication(medication1.getPharmaCentralNumber(), 1);
        } catch (MedicationNotfound e) {
            assertEquals(e.getMessage(), "No such Medication found!");
        }

        verify(medicationRepositoryMock, times(1)).findById(medication1.getPharmaCentralNumber());
        verify(medicationRepositoryMock, times(0)).save(medication1);
    }

    @Test
    void orderMedicationSuccess() throws NotEnoughMedicationInStock, MedicationNotfound {
        when(medicationRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(medication1));
        medicationServiceMock.order(order1);
        Optional<Medication> medication = medicationRepositoryMock.findById(medication1.getPharmaCentralNumber());
        assertEquals(medication1.getAvailable(), medication.get().getAvailable());

        verify(medicationRepositoryMock, times(2)).findById(medication1.getPharmaCentralNumber());
        verify(medicationRepositoryMock, times(1)).save(medication1);
    }

    @Test
    void orderMedicationNotEnough() throws MedicationNotfound {
        when(medicationRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(medication2));
        try {
            medicationServiceMock.order(order2);
        } catch (NotEnoughMedicationInStock e) {
            assertEquals(e.getMessage(), "Not enough medicine in the Stock!");
        }

        verify(medicationRepositoryMock, times(1)).findById(medication2.getPharmaCentralNumber());
    }

    @Test
    void orderMedicationNegativeNumber() throws NotEnoughMedicationInStock, MedicationNotfound {
        try {
            medicationServiceMock.order(order3);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Can not order a negative number!");
        }
    }

}