package com.apnapg.dto.review;
import java.time.Instant;

public record ReviewResponseDTO(

        Long id,

        Long pgId,

        Long tenantId,

        Integer rating,

        String comment,

        Instant createdAt

) {}

