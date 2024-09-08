package com.resturent.resturen_spring.service.auth;

import com.resturent.resturen_spring.entities.FoodItems;
import com.resturent.resturen_spring.entities.Foododer;
import com.resturent.resturen_spring.entities.OrderItem;
import com.resturent.resturen_spring.entities.User;
import com.resturent.resturen_spring.repositories.FoodItemRespository;
import com.resturent.resturen_spring.repositories.OrderRepository;
import com.resturent.resturen_spring.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodItemRespository foodItemRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, FoodItemRespository foodItemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.foodItemRepository = foodItemRepository;
        this.userRepository = userRepository;
    }

    public Foododer createOrder(Long userId,List<Long> foodItemIds, List<Integer> quantities, LocalDateTime orderdate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Foododer order = new Foododer();

        // Create a mock user for testing


        // Set the user for the order
        order.setUser(user);
        order.setStatus("Pending");
        order.setOrderDate(orderdate);

        // Save the user if necessary
        if (!userRepository.existsById(user.getId())) {
            userRepository.save(user); // Save only if the user doesn't exist
        }

        // Save the order first to generate an order ID
        Foododer savedOrder = orderRepository.save(order);

        // Prepare the order items and associate them with the saved order
        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0.0;

        for (int i = 0; i < foodItemIds.size(); i++) {
            FoodItems foodItem = foodItemRepository.findById(foodItemIds.get(i))
                    .orElseThrow(() -> new RuntimeException("Food item not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setFoodItem(foodItem);
            orderItem.setQuantity(quantities.get(i));
            orderItem.setPrice(foodItem.getPrice() * quantities.get(i));

            // Set the order for each order item
            orderItem.setOrder(savedOrder);

            orderItems.add(orderItem);
            totalPrice += orderItem.getPrice();
        }

        // Set order items and total price for the order
        savedOrder.setOrderItems(orderItems);
        savedOrder.setTotalPrice(totalPrice);

        // Save the order again with the order items
        return orderRepository.save(savedOrder);
    }



    // Month-end summary of orders and sales
    public Map<String, Object> getMonthEndSummary() {
        // Get the start and end of the current month
        LocalDateTime startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime endOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);

        // Fetch all orders within the current month
        List<Foododer> monthlyOrders = orderRepository.findOrdersByDateRange(startOfMonth, endOfMonth);

        // Variables to hold the summary data
        int totalOrders = monthlyOrders.size();
        double totalRevenue = 0.0;
        Map<String, Integer> itemCountMap = new HashMap<>();

        // Loop through each order to calculate total revenue and quantity sold for each item
        for (Foododer order : monthlyOrders) {
            Double totalPrice = order.getTotalPrice();
            totalRevenue += (totalPrice != null ? totalPrice : 0.0);

            for (OrderItem orderItem : order.getOrderItems()) {
                String foodItemName = orderItem.getFoodItem().getName();
                int quantity = orderItem.getQuantity();

                // Add quantity for each food item sold
                itemCountMap.put(foodItemName, itemCountMap.getOrDefault(foodItemName, 0) + quantity);
            }
        }

        // Prepare the summary data
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalOrders", totalOrders);
        summary.put("totalRevenue", totalRevenue);
        summary.put("itemCountMap", itemCountMap); // Item-wise quantity sold

        return summary;
    }

    public List<Foododer> getAllFoodOrders() {
        return orderRepository.findAll();
    }

    public void deleteFoodOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new ResourceNotFound("Food order not found with id " + id);
        }
    }
}
