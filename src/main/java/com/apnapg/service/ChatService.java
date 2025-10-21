package com.apnapg.service;

import com.apnapg.dto.ChatMessageDTO;
import com.apnapg.entity.ChatMessage;
import com.apnapg.repositories.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageDTO sendMessage(ChatMessageDTO dto) {
        ChatMessage chatMessage = ChatMessage.builder()
                .senderEmailId(dto.senderEmailId())
                .recipientEmailId(dto.recipientEmailId())
                .message(dto.message())
                .timestamp(LocalDateTime.now())
                .seen(false)
                .build();

        ChatMessage saved = chatMessageRepository.save(chatMessage);
        return toDTO(saved);
    }

    public List<ChatMessageDTO> getChatHistory(String email1, String email2) {
        List<ChatMessage> msgs1 = chatMessageRepository.findBySenderEmailIdAndRecipientEmailIdOrderByTimestampAsc(email1, email2);
        List<ChatMessage> msgs2 = chatMessageRepository.findByRecipientEmailIdAndSenderEmailIdOrderByTimestampAsc(email1, email2);
        List<ChatMessage> combined = new ArrayList<>();
        combined.addAll(msgs1);
        combined.addAll(msgs2);

        return combined.stream()
                .sorted(Comparator.comparing(ChatMessage::getTimestamp))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ChatMessageDTO toDTO(ChatMessage entity) {
        return new ChatMessageDTO(
                entity.getId(),
                entity.getSenderEmailId(),
                entity.getRecipientEmailId(),
                entity.getMessage(),
                entity.getTimestamp(),
                entity.getSeen()
        );
    }
}
