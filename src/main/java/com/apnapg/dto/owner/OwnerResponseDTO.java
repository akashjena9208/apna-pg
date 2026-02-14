package com.apnapg.dto.owner;

public record OwnerResponseDTO(

        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String businessName,
        String profileImageUrl
) {}
