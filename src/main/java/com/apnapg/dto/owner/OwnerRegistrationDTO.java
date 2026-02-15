package com.apnapg.dto.owner;

import jakarta.validation.constraints.*;

public record OwnerRegistrationDTO(

        @NotBlank
        @Size(min = 2, max = 50)
        String firstName,

        @NotBlank
        @Size(min = 2, max = 50)
        String lastName,

        @Email
        @NotBlank
        @Size(max = 120)
        String email,

        @NotBlank
        @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number")
        String phoneNumber,

        @Size(max = 255)
        String address,

        @NotBlank
        @Size(min = 2, max = 100)
        String businessName,

        @Pattern(
                regexp = "^([0-9]{2}[A-Z]{5}[0-9]{4}[A-Z][1-9A-Z]Z[0-9A-Z])?$",
                message = "Invalid GST number"
        )
        String gstNumber,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,20}$",
                message = "Password must contain upper, lower, number, special char"
        )
        String password
) {}
