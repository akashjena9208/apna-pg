package com.apnapg.dto.tenant;
import com.apnapg.enums.Gender;
import com.apnapg.enums.Occupation;
import java.time.LocalDate;
public record TenantUpdateDTO(
        String firstName,
        String lastName,
        String phoneNumber,
        Gender gender,
        Occupation occupation,
        LocalDate dateOfBirth,
        String address,
        String emergencyContactName,
        String emergencyContactNumber
) {}
