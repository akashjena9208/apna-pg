package com.apnapg.dto.tenant;

public record TenantResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String aadhaarUrl,
        Long roomId
) {}
