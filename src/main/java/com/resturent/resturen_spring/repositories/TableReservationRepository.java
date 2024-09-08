

package com.resturent.resturen_spring.repositories;

import com.resturent.resturen_spring.entities.TableReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface TableReservationRepository extends JpaRepository<TableReservation, Long> {

    @Query("SELECT tr FROM TableReservation tr WHERE tr.startTime BETWEEN :startTime AND :endTime")
    List<TableReservation> findByReservationTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT tr FROM TableReservation tr WHERE tr.tableNumber = :tableNumber AND tr.startTime BETWEEN :startTime AND :endTime")
    List<TableReservation> findByTableNumberAndReservationTimeBetween(@Param("tableNumber") int tableNumber, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
