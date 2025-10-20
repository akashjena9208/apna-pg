package com.apnapg.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewDTO(
        @NotNull Long tenantId,
        @NotNull Long pgId,
        @Min(1) @Max(5) Integer rating,
        @Size(max = 500)String comment
) {}


