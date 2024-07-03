package com.wsd.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private LocalDate saleDate;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
