package com.apnapg.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record ChatMessageDTO(
        Long id,
        @NotBlank String senderEmailId,
        @NotBlank String recipientEmailId,
        @NotBlank @Size(max = 2000) String message,
        LocalDateTime timestamp,
        Boolean seen
) {}
