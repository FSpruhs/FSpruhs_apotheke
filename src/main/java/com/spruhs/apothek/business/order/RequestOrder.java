package com.spruhs.apothek.business.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class RequestOrder {

    @NotNull
    private long pharmaCentralNumber;

    @NotNull
    private int number;

    @NotBlank
    private String storeName;


}
