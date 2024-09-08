package com.resturent.resturen_spring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Foododer order;

    @ManyToOne
    private FoodItems foodItem;

    private Integer quantity;
    private Double price;
}
