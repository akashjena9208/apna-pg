package com.apnapg.dto;

import jakarta.validation.constraints.*;

public record OwnerRegistrationDTO(
        @NotBlank @Size(min = 2, max = 50) String firstName,
        @NotBlank @Size(min = 2, max = 50) String lastName,
        @Email @NotBlank String email,
        @NotBlank @Pattern(regexp = "\\d{10}") String phoneNumber,
        @Size(max = 255) String address,
        @Size(min = 2, max = 100) String businessName,
        @Size(max = 20) String gstNumber,
        @NotBlank @Size(min = 6) String password
) {}
