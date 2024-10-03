package com.fawry.fawrymall.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderDto(
        @NotNull(message = "Customer ID required")
        Long customerId,
        @NotNull(message = "Order items required")
        List<OrderItemDto> items
) {
}
