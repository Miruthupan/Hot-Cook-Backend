package com.resturent.resturen_spring.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Data
public class TableReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private int tableNumber;

    @Column(nullable = false)
    private int numberOfPeople;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
