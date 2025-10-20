package com.apnapg.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactMessageDTO(
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name can be at most 100 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Subject is required")
        @Size(max = 150, message = "Subject can be at most 150 characters")
        String subject,

        @NotBlank(message = "Message is required")
        @Size(max = 2000, message = "Message can be at most 2000 characters")
        String message
) {}
