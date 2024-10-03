package com.fawry.fawrymall.dto;

import jakarta.validation.constraints.NotNull;

public record OrderItemDto(
    @NotNull(message = "Product ID required")
    Long productId,
    @NotNull(message = "Quantity required")
    int quantity
) {
}
