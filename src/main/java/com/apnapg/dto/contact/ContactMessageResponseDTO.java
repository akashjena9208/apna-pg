package com.apnapg.dto.contact;


import java.time.Instant;

public record ContactMessageResponseDTO(

        Long id,
        String name,
        String email,
        String subject,
        String message,
        boolean resolved,
        Instant createdAt

) {}

