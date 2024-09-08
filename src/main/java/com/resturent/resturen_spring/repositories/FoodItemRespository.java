package com.resturent.resturen_spring.repositories;

import com.resturent.resturen_spring.entities.FoodItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRespository extends JpaRepository <FoodItems, Long> {
}
