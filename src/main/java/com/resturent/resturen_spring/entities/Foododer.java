package com.resturent.resturen_spring.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Foododer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user; // Link to the User who made the order

    private String status; // Pending, Completed, Cancelled, etc.

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    private Double totalPrice;
    private LocalDateTime orderDate;

}
