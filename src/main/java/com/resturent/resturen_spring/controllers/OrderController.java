package com.resturent.resturen_spring.controllers;


import com.resturent.resturen_spring.dtos.OrderRequest;
import com.resturent.resturen_spring.entities.FoodItems;
import com.resturent.resturen_spring.entities.Foododer;
import com.resturent.resturen_spring.entities.TableReservation;
import com.resturent.resturen_spring.entities.User;
import com.resturent.resturen_spring.service.auth.FoodItemService;
import com.resturent.resturen_spring.service.auth.OrderService;
import com.resturent.resturen_spring.service.auth.ReservationService;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final FoodItemService foodItemService;

    public OrderController(OrderService orderService, FoodItemService foodItemService) {
        this.orderService = orderService;
        this.foodItemService = foodItemService;
    }

    @PostMapping("/create")
    public Foododer createOrder(@RequestBody OrderRequest orderRequest) {
        Long userId = orderRequest.getUserId();
        return orderService.createOrder(userId,orderRequest.getFoodItemIds(), orderRequest.getQuantities(), orderRequest.getOrderDate());

    }


    // Get all food items
    @GetMapping("/getAllFoods")
    public ResponseEntity<List<FoodItems>> getAllFoodItems() {
        List<FoodItems> foodItems = foodItemService.getAllFoodItems();
        return ResponseEntity.ok(foodItems);
    }

    // Get a food item by ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodItems> getFoodItemById(@PathVariable Long id) {
        FoodItems foodItem = foodItemService.getFoodItemById(id);
        return ResponseEntity.ok(foodItem);
    }

    // Add a new food item with an image
    @PostMapping("/add")
    public ResponseEntity<FoodItems> addFoodItem(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam MultipartFile imageFile) throws IOException {

        FoodItems foodItem = new FoodItems();
        foodItem.setName(name);
        foodItem.setDescription(description);
        foodItem.setPrice(price);

        FoodItems savedFoodItem = foodItemService.addFoodItem(foodItem, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFoodItem);
    }

    // Update an existing food item with an image
    @PutMapping("/update/{id}")
    public ResponseEntity<FoodItems> updateFoodItem(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) MultipartFile imageFile) throws IOException {

        FoodItems updatedFoodItem = new FoodItems();
        updatedFoodItem.setName(name);
        updatedFoodItem.setDescription(description);
        updatedFoodItem.setPrice(price);

        FoodItems foodItem = foodItemService.updateFoodItem(id, updatedFoodItem, imageFile);
        return ResponseEntity.ok(foodItem);
    }

    // Delete a food item
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to get the month-end summary
    @GetMapping("/summary/month-end")
    public ResponseEntity<Map<String, Object>> getMonthEndSummary() {
        Map<String, Object> summary = orderService.getMonthEndSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Foododer>> getAllFoodOrders() {
        List<Foododer> foodOrders = orderService.getAllFoodOrders();
        return ResponseEntity.ok(foodOrders);
    }

    // Delete a food order
    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<Void> deleteFoodOrder(@PathVariable Long id) {
        orderService.deleteFoodOrder(id);
        return ResponseEntity.noContent().build();
    }

}
