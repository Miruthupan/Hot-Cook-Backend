package com.resturent.resturen_spring.repositories;

import com.resturent.resturen_spring.entities.Foododer;
import com.resturent.resturen_spring.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository <Foododer, Long>{

    @Query("SELECT o FROM Foododer o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<Foododer> findOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

}
