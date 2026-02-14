package com.apnapg.dto.chat;

import jakarta.validation.constraints.*;

public record ChatSendMessageDTO(

        @NotNull(message = "Recipient is required")
        Long recipientId,

        @NotBlank(message = "Message cannot be empty")
        @Size(max = 2000, message = "Message too long")
        String message

) {}
