package com.resturent.resturen_spring.controllers;

import com.resturent.resturen_spring.entities.TableReservation;
import com.resturent.resturen_spring.service.auth.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;


    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<TableReservation> createReservation(@RequestParam String customerName,
                                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
                                                              @RequestParam int tableNumber,
                                                              @RequestParam int numberOfPeople,
                                                              @RequestParam Long userId) {
        TableReservation reservation = reservationService.createReservation(customerName, startTime,endTime, tableNumber, numberOfPeople, userId);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/for-date")
    public ResponseEntity<List<TableReservation>> getReservationsForDate(@RequestParam LocalDateTime date) {
        List<TableReservation> reservations = reservationService.getReservationsForDate(date);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/for-table")
    public ResponseEntity<List<TableReservation>> getReservationsForTable(@RequestParam int tableNumber,
                                                                          @RequestParam LocalDateTime date) {
        List<TableReservation> reservations = reservationService.getReservationsForTable(tableNumber, date);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TableReservation>> getAllReservations() {
        List<TableReservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    // New: Delete Reservation by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
