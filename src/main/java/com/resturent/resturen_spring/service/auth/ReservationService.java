package com.resturent.resturen_spring.service.auth;


import com.resturent.resturen_spring.entities.TableReservation;
import com.resturent.resturen_spring.entities.User;
import com.resturent.resturen_spring.repositories.TableReservationRepository;
import com.resturent.resturen_spring.repositories.UserRepository;
import com.resturent.resturen_spring.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class
ReservationService {
    private final TableReservationRepository tableReservationRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public ReservationService(TableReservationRepository tableReservationRepository, UserRepository userRepository, EmailService emailService) {
        this.tableReservationRepository = tableReservationRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public TableReservation createReservation(String customerName, LocalDateTime startTime,LocalDateTime endTime, int tableNumber, int numberOfPeople, Long userId) {
        TableReservation reservation = new TableReservation();

        // Create a mock user for testing or find the existing user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<TableReservation> reserveTable = tableReservationRepository.findByTableNumberAndReservationTimeBetween(tableNumber,startTime,endTime);

        if(!reserveTable.isEmpty()) {
            throw new RuntimeException("Table already booked");
        }

        reservation.setCustomerName(customerName);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);

        reservation.setTableNumber(tableNumber);
        reservation.setNumberOfPeople(numberOfPeople);
        reservation.setUser(user);

        // Save the reservation
        TableReservation savedReservation = tableReservationRepository.save(reservation);

        // Send confirmation email
        emailService.sendReservationConfirmation(user.getEmail(), customerName, startTime, endTime, tableNumber, numberOfPeople);

        // Save the reservation
        return tableReservationRepository.save(reservation);
    }

    public List<TableReservation> getReservationsForDate(LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = date.toLocalDate().atTime(23, 59, 59);
        return tableReservationRepository.findByReservationTimeBetween(startOfDay, endOfDay);
    }

    public List<TableReservation> getReservationsForTable(int tableNumber, LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = date.toLocalDate().atTime(23, 59, 59);
        return tableReservationRepository.findByTableNumberAndReservationTimeBetween(tableNumber, startOfDay, endOfDay);
    }

    public List<TableReservation> getAllReservations() {
        return tableReservationRepository.findAll();
    }

    // New: Delete Reservation by ID
    public void deleteReservation(Long id) {
        tableReservationRepository.deleteById(id);
    }

}
