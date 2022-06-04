package com.spruhs.apothek.presentation;

import com.spruhs.apothek.business.Medication;
import com.spruhs.apothek.business.MedicationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class AllUsersControllerTest {

    @InjectMocks
    AllUsersController allUsersController;

    @MockBean
    MedicationService medicationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /all Medications")
    public void testGetAllMedication() throws Exception {

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

        //when(medicationService.getMedicationById(eq(medication1.getPharmaCentralNumber()))).thenReturn(new ResponseEntity<Medication>());

        mockMvc.perform(get("/pharmacy/allUsers/medication/?id=" + medication1.getPharmaCentralNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.producer", is(medication1.getProducer())));

    }

}