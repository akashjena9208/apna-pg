package com.apnapg.dto.pg;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PGCreateRequest(

        @NotBlank
        @Size(min = 3, max = 100)
        String name,

        @NotBlank
        @Size(min = 10, max = 255)
        String address,

        @NotBlank
        @Size(min = 2, max = 100)
        String city,

        @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid contact number")
        String contactNumber,

        @NotNull
        @Positive(message = "Rent must be positive")
        BigDecimal rentPerMonth,

        @NotNull
        @Min(1)
        @Max(500)
        Integer totalRooms
) {}
