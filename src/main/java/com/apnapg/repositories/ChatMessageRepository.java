package com.apnapg.repositories;

import com.apnapg.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderEmailIdAndRecipientEmailIdOrderByTimestampAsc(String senderEmailId, String recipientEmailId);
    List<ChatMessage> findByRecipientEmailIdAndSenderEmailIdOrderByTimestampAsc(String recipientEmailId, String senderEmailId);
}
