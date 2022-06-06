package com.spruhs.apothek.business.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class OrderJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long pharmaCentralNumber;

    @Column
    private int number;

    @Column
    private String storeName;

    @Column
    @UpdateTimestamp
    private LocalDate date;

    @Column
    private OrderStatus orderStatus = OrderStatus.RECEIVED;

}
