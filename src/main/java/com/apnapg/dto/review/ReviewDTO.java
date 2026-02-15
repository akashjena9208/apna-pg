package com.apnapg.dto.review;

import jakarta.validation.constraints.*;

public record ReviewDTO(

        @NotNull(message = "Tenant ID is required")
        Long tenantId,

        @NotNull(message = "PG ID is required")
        Long pgId,

        @NotNull(message = "Rating is required")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating cannot exceed 5")
        Integer rating,

        @Size(max = 500, message = "Comment cannot exceed 500 characters")
        String comment

) {
}
