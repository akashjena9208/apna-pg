package com.apnapg.dto.tenant;

import com.apnapg.enums.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record TenantRegistrationDTO(

        @NotBlank @Size(min = 2, max = 50)
        String firstName,

        @NotBlank @Size(min = 2, max = 50)
        String lastName,

        @Email @NotBlank @Size(max = 120)
        String email,

        @NotBlank
        @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number")
        String phoneNumber,

        @NotNull
        Gender gender,

        @NotNull
        Occupation occupation,

        @NotNull @Past
        LocalDate dateOfBirth,

        @NotBlank @Size(min = 15, max = 200)
        String address,

        @NotBlank @Size(min = 2, max = 50)
        String emergencyContactName,

        @NotBlank
        @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number")
        String emergencyContactNumber,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,20}$",
                message = "Password must contain upper, lower, number, special char"
        )
        String password
) {}

