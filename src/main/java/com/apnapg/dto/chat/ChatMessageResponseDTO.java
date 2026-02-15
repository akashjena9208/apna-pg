package com.apnapg.dto.chat;

import java.time.Instant;

public record ChatMessageResponseDTO(

        Long id,
        Long senderId,
        Long recipientId,
        String message,
        Instant createdAt,
        Boolean seen

) {}
