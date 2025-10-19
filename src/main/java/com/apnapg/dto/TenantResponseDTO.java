package com.apnapg.dto;

public record TenantResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String aadhaarUrl
) {}
