package com.fawry.fawrymall.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MerchantDto(
        @NotNull(message = "Merchant first name required")
        @NotBlank(message = "Merchant first name required")
        String firstName,
        @NotNull(message = "Merchant last name required")
        @NotBlank(message = "Merchant last name required")
        String lastName,
        @NotNull(message = "Merchant email required")
        @NotBlank(message = "Merchant email required")
        @Email(message = "Email not valid")
        String email,
        String phone
) {
}
