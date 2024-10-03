package com.fawry.fawrymall.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDto(
        @NotBlank(message = "Product name required")
        String name,
        @NotNull(message = "Product price required")
        double price,
        @NotNull(message = "Category ID required")
        Long categoryId,
        @NotNull(message = "Merchant ID required")
        Long merchantId
) {
}
