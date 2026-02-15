package com.apnapg.dto.contact;

import jakarta.validation.constraints.*;

public record ContactMessageDTO(

        @NotBlank
        @Size(min = 2, max = 100)
        String name,

        @Email
        @NotBlank
        @Size(max = 120)
        String email,

        @NotBlank
        @Size(min = 5, max = 150)
        String subject,

        @NotBlank
        @Size(min = 10, max = 2000)
        String message

) {}
