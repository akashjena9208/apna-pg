package com.apnapg.service;

import com.apnapg.dto.chat.*;

import java.util.List;

public interface ChatService {

    ChatMessageResponseDTO sendMessage(Long senderId, ChatSendMessageDTO dto);

    List<ChatMessageResponseDTO> getConversation(Long user1, Long user2);

    List<ChatHistoryDTO> getChatHistory(Long userId);

    void markAsSeen(Long messageId);
}
