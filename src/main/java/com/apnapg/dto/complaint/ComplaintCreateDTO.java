package com.apnapg.dto.complaint;

import jakarta.validation.constraints.*;

public record ComplaintCreateDTO(

        @NotNull(message = "PG id is required")
        Long pgId,

        @NotBlank(message = "Complaint message is required")
        @Size(min = 10, max = 1000, message = "Complaint must be 10–1000 characters")
        String message

) {}

