package com.apnapg.dto;

import com.apnapg.enums.Gender;
import com.apnapg.enums.Occupation;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record TenantRegistrationDTO(
        @NotBlank @Size(min = 2, max = 50) String firstName,
        @NotBlank @Size(min = 2, max = 50) String lastName,
        @Email @NotBlank String email,
        @NotBlank @Pattern(regexp = "\\d{10}") String phoneNumber,
        @NotNull Gender gender,
        @NotNull Occupation occupation,
        @NotNull LocalDate dateOfBirth,
        @NotBlank @Size(min = 10, max = 200) String address,
        @NotBlank @Size(min = 2, max = 50) String emergencyContactName,
        @NotBlank @Pattern(regexp = "\\d{10}") String emergencyContactNumber,
        @NotBlank @Size(min = 6) String password
) {}
