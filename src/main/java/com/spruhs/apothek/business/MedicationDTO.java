package com.spruhs.apothek.business;

import lombok.Data;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
@Data
public class MedicationDTO {
    long pharmaCentralNumber;
    String name;
    int available;

}
