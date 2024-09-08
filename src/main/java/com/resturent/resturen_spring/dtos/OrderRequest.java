package com.resturent.resturen_spring.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequest {
    private Long userId;
    private List<Long> foodItemIds;
    private List<Integer> quantities;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDate;
}
