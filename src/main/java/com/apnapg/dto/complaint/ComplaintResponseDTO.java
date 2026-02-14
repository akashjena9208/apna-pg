package com.apnapg.dto.complaint;

import com.apnapg.enums.ComplaintStatus;
import java.time.Instant;

public record ComplaintResponseDTO(

        Long id,
        Long pgId,
        Long tenantId,
        String message,
        ComplaintStatus status,
        Instant createdAt,
        Instant updatedAt

) {}
