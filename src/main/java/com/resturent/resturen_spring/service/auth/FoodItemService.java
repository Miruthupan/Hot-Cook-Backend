package com.resturent.resturen_spring.service.auth;

import com.resturent.resturen_spring.entities.FoodItems;
import com.resturent.resturen_spring.repositories.FoodItemRespository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FoodItemService {
    private final FoodItemRespository foodItemRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    public FoodItemService(FoodItemRespository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    public List<FoodItems> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    public FoodItems getFoodItemById(Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));
    }

    public FoodItems addFoodItem(FoodItems foodItem, MultipartFile imageFile) throws IOException {
        String imageUrl = saveImage(imageFile);
        foodItem.setImageUrl(imageUrl);
        return foodItemRepository.save(foodItem);
    }

    public FoodItems updateFoodItem(Long id, FoodItems updatedFoodItem, MultipartFile imageFile) throws IOException {
        FoodItems existingFoodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        existingFoodItem.setName(updatedFoodItem.getName());
        existingFoodItem.setDescription(updatedFoodItem.getDescription());
        existingFoodItem.setPrice(updatedFoodItem.getPrice());

        if (!imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile);
            existingFoodItem.setImageUrl(imageUrl);
        }

        return foodItemRepository.save(existingFoodItem);
    }

    public void deleteFoodItem(Long id) {
        FoodItems existingFoodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));
        foodItemRepository.delete(existingFoodItem);
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            throw new RuntimeException("Image file is empty.");
        }

        String fileName = imageFile.getOriginalFilename();
        Path imagePath = Paths.get(UPLOAD_DIR + fileName);

        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, imageFile.getBytes());

        return "/images/" + fileName; // Path for accessing the image in the static directory
    }
}
