package com.fawry.fawrymall.dto;

public record OrderItemDto(
    Long productId,
    int quantity
) {
}
