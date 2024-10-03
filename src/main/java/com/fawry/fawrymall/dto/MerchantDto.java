package com.fawry.fawrymall.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MerchantDto(
        @NotBlank(message = "Merchant first name required")
        String firstName,
        @NotBlank(message = "Merchant last name required")
        String lastName,
        @NotBlank(message = "Merchant email required")
        @Email(message = "Email not valid")
        String email,
        String phone
) {
}
