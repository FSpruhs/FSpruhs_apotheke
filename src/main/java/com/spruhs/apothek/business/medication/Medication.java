package com.spruhs.apothek.business.medication;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medication")
public class Medication {

    @Id
    private long pharmaCentralNumber;

    @Column
    @NotBlank
    private String name;

    @Column
    @NotBlank
    private String ingredients;

    @Column
    @NotBlank
    private String producer;

    @Column()
    private int available = 0;

}
