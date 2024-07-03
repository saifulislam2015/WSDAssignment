package com.wsd.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
//
//    @OneToMany(mappedBy = "item")
//    private List<Sale> sales;
//
//    @OneToMany(mappedBy = "item")
//    private List<Wishlist> wishlists;
}
