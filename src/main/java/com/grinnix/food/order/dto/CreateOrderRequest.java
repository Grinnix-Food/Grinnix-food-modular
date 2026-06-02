package com.grinnix.food.order.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateOrderRequest(
    @NotBlank
    String ownerName,
    @NotBlank String address
) {
}