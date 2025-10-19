package com.apnapg.dto;

import jakarta.validation.constraints.*;

public record PGCreateRequest(
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String city,
        @Pattern(regexp = "\\d{10}") String contactNumber,
        @NotNull Double rentPerMonth,
        @NotNull Integer totalRooms
) {}
