
package com.apnapg.repository;

import com.apnapg.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByConversationIdOrderByCreatedAtAsc(String conversationId);

    List<ChatMessage> findBySenderIdOrRecipientId(Long senderId, Long recipientId);

    List<ChatMessage> findBySender_IdOrRecipient_Id(Long senderId, Long recipientId);

}
