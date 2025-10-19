package com.apnapg.dto;

public record OwnerResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String businessName,
        String profileImageUrl
) {}
