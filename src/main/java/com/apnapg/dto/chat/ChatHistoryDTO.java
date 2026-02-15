package com.apnapg.dto.chat;

public record ChatHistoryDTO(
        Long chatUserId,
        String chatUserName,
        String lastMessage,
        Boolean seen
) {}
