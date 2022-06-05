package com.spruhs.apothek.business.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
@Data
public class RequestOrder {

    @NotNull
    private long pharmaCentralNumber;

    @NotNull
    private int number;

    @NotBlank
    private String storeName;


}
