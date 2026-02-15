package com.apnapg.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is required")
        @Size(max = 120, message = "Email must not exceed 120 characters")
        String email,

        @NotBlank(message = "Password is required")
        String password

) {}