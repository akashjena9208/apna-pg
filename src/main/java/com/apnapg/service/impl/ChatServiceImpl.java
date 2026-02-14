////package com.apnapg.service.impl;
////
////import com.apnapg.dto.chat.ChatHistoryDTO;
////import com.apnapg.dto.chat.ChatMessageResponseDTO;
////import com.apnapg.dto.chat.ChatSendMessageDTO;
////import com.apnapg.entity.ChatMessage;
////import com.apnapg.entity.User;
////import com.apnapg.exceptions.BadRequestException;
////import com.apnapg.exceptions.ForbiddenOperationException;
////import com.apnapg.exceptions.ResourceNotFoundException;
////import com.apnapg.mappers.ChatMessageMapper;
////import com.apnapg.repository.ChatMessageRepository;
////import com.apnapg.repository.UserRepository;
////import com.apnapg.service.ChatService;
////import lombok.RequiredArgsConstructor;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.stereotype.Service;
////import org.springframework.transaction.annotation.Transactional;
////
////import java.util.Comparator;
////import java.util.List;
////import java.util.Map;
////import java.util.stream.Collectors;
////
////@Service
////@RequiredArgsConstructor
////@Slf4j
////@Transactional
////public class ChatServiceImpl implements ChatService {
////
////    private final ChatMessageRepository chatRepository;
////    private final UserRepository userRepository;
////    private final ChatMessageMapper chatMapper;
////
////    // ======================================================
////    // SEND MESSAGE
////    // ======================================================
////    @Override
////    public ChatMessageResponseDTO sendMessage(Long senderId, ChatSendMessageDTO dto) {
////
////        if (senderId.equals(dto.recipientId()))
////            throw new BadRequestException("You cannot message yourself");
////
////        User sender = userRepository.findById(senderId)
////                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));
////
////        User recipient = userRepository.findById(dto.recipientId())
////                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found"));
////
////        String conversationId = buildConversationId(senderId, dto.recipientId());
////
////        ChatMessage message = ChatMessage.builder()
////                .conversationId(conversationId)
////                .sender(sender)
////                .recipient(recipient)
////                .message(dto.message())
////                .seen(false)
////                .build();
////
////        chatRepository.save(message);
////
////        log.info("Message sent from {} to {}", senderId, dto.recipientId());
////
////        return chatMapper.toResponseDTO(message);
////    }
////
////    // ======================================================
////    // GET CONVERSATION (SECURE)
////    // ======================================================
////    @Override
////    @Transactional(readOnly = true)
////    public List<ChatMessageResponseDTO> getConversation(Long user1, Long user2) {
////
////        String conversationId = buildConversationId(user1, user2);
////
////        return chatRepository
////                .findByConversationIdOrderByCreatedAtAsc(conversationId)
////                .stream()
////                .map(chatMapper::toResponseDTO)
////                .toList();
////    }
////
////    // ======================================================
////    // GET CHAT HISTORY
////    // ======================================================
////    @Override
////    @Transactional(readOnly = true)
////    public List<ChatHistoryDTO> getChatHistory(Long userId) {
////
////        List<ChatMessage> messages =
////                chatRepository.findBySenderIdOrRecipientId(userId, userId);
////
////        Map<String, List<ChatMessage>> grouped =
////                messages.stream()
////                        .collect(Collectors.groupingBy(ChatMessage::getConversationId));
////
////        return grouped.values()
////                .stream()
////                .map(list -> list.stream()
////                        .max(Comparator.comparing(ChatMessage::getCreatedAt))
////                        .orElseThrow())
////                .map(msg -> {
////
////                    boolean isSender = msg.getSender().getId().equals(userId);
////
////                    Long otherUserId = isSender
////                            ? msg.getRecipient().getId()
////                            : msg.getSender().getId();
////
////                    boolean seen = isSender || msg.isSeen();
////
////                    return new ChatHistoryDTO(
////                            otherUserId,
////                            null,
////                            msg.getMessage(),
////                            seen
////                    );
////                })
////                .toList();
////    }
////
////    // ======================================================
////    // MARK AS SEEN (SECURE)
////    // ======================================================
////    @Override
////    public void markAsSeen(Long messageId) {
////
////        ChatMessage message = chatRepository.findById(messageId)
////                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
////
////        message.setSeen(true);
////
////        log.debug("Message {} marked as seen", messageId);
////    }
////
////    // ======================================================
////    // HELPER
////    // ======================================================
////    private String buildConversationId(Long user1, Long user2) {
////        return user1 < user2
////                ? user1 + "_" + user2
////                : user2 + "_" + user1;
////    }
////}
//package com.apnapg.service.impl;
//
//import com.apnapg.dto.chat.ChatHistoryDTO;
//import com.apnapg.dto.chat.ChatMessageResponseDTO;
//import com.apnapg.dto.chat.ChatSendMessageDTO;
//import com.apnapg.entity.ChatMessage;
//import com.apnapg.entity.User;
//import com.apnapg.exceptions.BadRequestException;
//import com.apnapg.exceptions.ResourceNotFoundException;
//import com.apnapg.mappers.ChatMessageMapper;
//import com.apnapg.repository.ChatMessageRepository;
//import com.apnapg.repository.UserRepository;
//import com.apnapg.security.SecurityUtils;
//import com.apnapg.service.ChatService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import org.springframework.transaction.annotation.Transactional;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//@Transactional
////public class ChatServiceImpl implements ChatService {
////
////    private final ChatMessageRepository chatRepository;
////    private final UserRepository userRepository;
////    private final ChatMessageMapper chatMapper;
////
////    // ======================================================
////    // SEND MESSAGE
////    // ======================================================
////    @Override
////    public ChatMessageResponseDTO sendMessage(Long ignored, ChatSendMessageDTO dto) {
////
////        Long senderUserId = SecurityUtils.getCurrentUserId();
////
////        if (senderUserId.equals(dto.recipientId()))
////            throw new BadRequestException("Cannot message yourself");
////
////        User sender = userRepository.findById(senderUserId)
////                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));
////
////        User recipient = userRepository.findById(dto.recipientId())
////                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found"));
////
////        String conversationId = buildConversationId(senderUserId, dto.recipientId());
////
////        ChatMessage message = ChatMessage.builder()
////                .conversationId(conversationId)
////                .sender(sender)
////                .recipient(recipient)
////                .message(dto.message())
////                .seen(false)
////                .build();
////
////        chatRepository.save(message);
////
////        return chatMapper.toResponseDTO(message);
////    }
////
////    // ======================================================
////    // GET CONVERSATION
////    // ======================================================
////    @Override
////    @Transactional(readOnly = true)
////    public List<ChatMessageResponseDTO> getConversation(Long user1, Long user2) {
////
////        String conversationId = buildConversationId(user1, user2);
////
////        return chatRepository
////                .findByConversationIdOrderByCreatedAtAsc(conversationId)
////                .stream()
////                .map(chatMapper::toResponseDTO)
////                .toList();
////    }
////
////    // ======================================================
////    // GET CHAT HISTORY
////    // ======================================================
////    @Override
////    @Transactional(readOnly = true)
////    public List<ChatHistoryDTO> getChatHistory(Long userId) {
////
////        List<ChatMessage> messages =
////                chatRepository.findBySenderIdOrRecipientId(userId, userId);
////
////        return messages.stream()
////                .collect(Collectors.groupingBy(ChatMessage::getConversationId))
////                .values()
////                .stream()
////                .map(list -> list.stream()
////                        .max(Comparator.comparing(ChatMessage::getCreatedAt))
////                        .orElseThrow())
////                .map(msg -> {
////
////                    Long otherUserId =
////                            msg.getSender().getId().equals(userId)
////                                    ? msg.getRecipient().getId()
////                                    : msg.getSender().getId();
////
////                    return new ChatHistoryDTO(
////                            otherUserId,
////                            null,
////                            msg.getMessage(),
////                            msg.isSeen()
////                    );
////                })
////                .toList();
////    }
////
////    // ======================================================
////    // MARK AS SEEN
////    // ======================================================
////    @Override
////    public void markAsSeen(Long messageId) {
////
////        ChatMessage message = chatRepository.findById(messageId)
////                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
////
////        message.setSeen(true);
////    }
////
////    // ======================================================
////    // HELPER
////    // ======================================================
////    private String buildConversationId(Long user1, Long user2) {
////        return user1 < user2
////                ? user1 + "_" + user2
////                : user2 + "_" + user1;
////    }
////}
//
//
//package com.apnapg.service.impl;
//
//import com.apnapg.dto.chat.ChatHistoryDTO;
//import com.apnapg.dto.chat.ChatMessageResponseDTO;
//import com.apnapg.dto.chat.ChatSendMessageDTO;
//import com.apnapg.entity.ChatMessage;
//import com.apnapg.entity.User;
//import com.apnapg.exceptions.BadRequestException;
//import com.apnapg.exceptions.ResourceNotFoundException;
//import com.apnapg.mappers.ChatMessageMapper;
//import com.apnapg.repository.ChatMessageRepository;
//import com.apnapg.repository.UserRepository;
//import com.apnapg.security.SecurityUtils;
//import com.apnapg.service.ChatService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import org.springframework.transaction.annotation.Transactional;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//@Transactional
//public class ChatServiceImpl implements ChatService {
//
//    private final ChatMessageRepository chatRepository;
//    private final UserRepository userRepository;
//    private final ChatMessageMapper chatMapper;
//
//    // ======================================================
//    // SEND MESSAGE
//    // ======================================================
//    @Override
//    public ChatMessageResponseDTO sendMessage(Long ignored, ChatSendMessageDTO dto) {
//
//        Long senderUserId = SecurityUtils.getCurrentUserId();
//
//        if (senderUserId.equals(dto.recipientId()))
//            throw new BadRequestException("Cannot message yourself");
//
//        User sender = userRepository.findById(senderUserId)
//                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));
//
//        User recipient = userRepository.findById(dto.recipientId())
//                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found"));
//
//        String conversationId = buildConversationId(senderUserId, dto.recipientId());
//
//        ChatMessage message = ChatMessage.builder()
//                .conversationId(conversationId)
//                .sender(sender)
//                .recipient(recipient)
//                .message(dto.message())
//                .seen(false)
//                .build();
//
//        chatRepository.save(message);
//
//        return chatMapper.toResponseDTO(message);
//    }
//
//    // ======================================================
//    // GET CONVERSATION
//    // ======================================================
//    @Override
//    @Transactional(readOnly = true)
//    public List<ChatMessageResponseDTO> getConversation(Long user1, Long user2) {
//
//        String conversationId = buildConversationId(user1, user2);
//
//        return chatRepository
//                .findByConversationIdOrderByCreatedAtAsc(conversationId)
//                .stream()
//                .map(chatMapper::toResponseDTO)
//                .toList();
//    }
//
//    // ======================================================
//    // GET CHAT HISTORY
//    // ======================================================
//    @Override
//    @Transactional(readOnly = true)
//    public List<ChatHistoryDTO> getChatHistory(Long userId) {
//
//        List<ChatMessage> messages =
//                chatRepository.findBySenderIdOrRecipientId(userId, userId);
//
//        return messages.stream()
//                .collect(Collectors.groupingBy(ChatMessage::getConversationId))
//                .values()
//                .stream()
//                .map(list -> list.stream()
//                        .max(Comparator.comparing(ChatMessage::getCreatedAt))
//                        .orElseThrow())
//                .map(msg -> {
//
//                    Long otherUserId =
//                            msg.getSender().getId().equals(userId)
//                                    ? msg.getRecipient().getId()
//                                    : msg.getSender().getId();
//
//                    return new ChatHistoryDTO(
//                            otherUserId,
//                            null,
//                            msg.getMessage(),
//                            msg.isSeen()
//                    );
//                })
//                .toList();
//    }
//
//    // ======================================================
//    // MARK AS SEEN
//    // ======================================================
//    @Override
//    public void markAsSeen(Long messageId) {
//
//        ChatMessage message = chatRepository.findById(messageId)
//                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
//
//        message.setSeen(true);
//    }
//
//    // ======================================================
//    // HELPER
//    // ======================================================
//    private String buildConversationId(Long user1, Long user2) {
//        return user1 < user2
//                ? user1 + "_" + user2
//                : user2 + "_" + user1;
//    }
//}

package com.apnapg.service.impl;

import com.apnapg.dto.chat.ChatHistoryDTO;
import com.apnapg.dto.chat.ChatMessageResponseDTO;
import com.apnapg.dto.chat.ChatSendMessageDTO;
import com.apnapg.entity.ChatMessage;
import com.apnapg.entity.User;
import com.apnapg.exceptions.BadRequestException;
import com.apnapg.exceptions.ForbiddenOperationException;
import com.apnapg.exceptions.ResourceNotFoundException;
import com.apnapg.mappers.ChatMessageMapper;
import com.apnapg.repository.ChatMessageRepository;
import com.apnapg.repository.UserRepository;
import com.apnapg.security.SecurityUtils;
import com.apnapg.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//@Transactional
//public class ChatServiceImpl implements ChatService {
//
//    private final ChatMessageRepository chatRepository;
//    private final UserRepository userRepository;
//    private final ChatMessageMapper chatMapper;
//
//    // ======================================================
//    // SEND MESSAGE
//    // ======================================================
//    @Override
//    public ChatMessageResponseDTO sendMessage(Long ignored, ChatSendMessageDTO dto) {
//
//        Long senderUserId = SecurityUtils.getCurrentUserId();
//
//        if (senderUserId.equals(dto.recipientId()))
//            throw new BadRequestException("Cannot message yourself");
//
//        User sender = userRepository.findById(senderUserId)
//                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));
//
//        User recipient = userRepository.findById(dto.recipientId())
//                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found"));
//
//        String conversationId = buildConversationId(senderUserId, dto.recipientId());
//
//        ChatMessage message = ChatMessage.builder()
//                .conversationId(conversationId)
//                .sender(sender)
//                .recipient(recipient)
//                .message(dto.message())
//                .seen(false)
//                .build();
//
//        chatRepository.save(message);
//
//        return chatMapper.toResponseDTO(message);
//    }
//
//    // ======================================================
//    // GET CONVERSATION
//    // ======================================================
//    @Override
//    @Transactional(readOnly = true)
//    public List<ChatMessageResponseDTO> getConversation(Long user1, Long user2) {
//
//        String conversationId = buildConversationId(user1, user2);
//
//        return chatRepository
//                .findByConversationIdOrderByCreatedAtAsc(conversationId)
//                .stream()
//                .map(chatMapper::toResponseDTO)
//                .toList();
//    }
//
//    // ======================================================
//    // GET CHAT HISTORY
//    // ======================================================
//    @Override
//    @Transactional(readOnly = true)
//    public List<ChatHistoryDTO> getChatHistory(Long userId) {
//
//        List<ChatMessage> messages =
//                chatRepository.findBySenderIdOrRecipientId(userId, userId);
//
//        return messages.stream()
//                .collect(Collectors.groupingBy(ChatMessage::getConversationId))
//                .values()
//                .stream()
//                .map(list -> list.stream()
//                        .max(Comparator.comparing(ChatMessage::getCreatedAt))
//                        .orElseThrow())
//                .map(msg -> {
//
//                    Long otherUserId =
//                            msg.getSender().getId().equals(userId)
//                                    ? msg.getRecipient().getId()
//                                    : msg.getSender().getId();
//
//                    return new ChatHistoryDTO(
//                            otherUserId,
//                            null,
//                            msg.getMessage(),
//                            msg.isSeen()
//                    );
//                })
//                .toList();
//    }
//
//    // ======================================================
//    // MARK AS SEEN
//    // ======================================================
//    @Override
//    public void markAsSeen(Long messageId) {
//
//        ChatMessage message = chatRepository.findById(messageId)
//                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
//
//        message.setSeen(true);
//    }
//
//    // ======================================================
//    // HELPER
//    // ======================================================
//    private String buildConversationId(Long user1, Long user2) {
//        return user1 < user2
//                ? user1 + "_" + user2
//                : user2 + "_" + user1;
//    }
//}
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatRepository;
    private final UserRepository userRepository;

    // ======================================================
    // SEND MESSAGE
    // ======================================================
    @Override
    public ChatMessageResponseDTO sendMessage(Long ignored, ChatSendMessageDTO dto) {

        Long senderUserId = SecurityUtils.getCurrentUserId();

        if (senderUserId.equals(dto.recipientId()))
            throw new BadRequestException("Cannot message yourself");

        User sender = userRepository.findById(senderUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

        User recipient = userRepository.findById(dto.recipientId())
                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found"));

        String conversationId = buildConversationId(senderUserId, dto.recipientId());

        ChatMessage message = ChatMessage.builder()
                .conversationId(conversationId)
                .sender(sender)
                .recipient(recipient)
                .message(dto.message())
                .seen(false)
                .build();

        chatRepository.save(message);

        return toResponseDTO(message);
    }

    // ======================================================
    // GET CONVERSATION
    // ======================================================
    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageResponseDTO> getConversation(Long user1, Long user2) {

        String conversationId = buildConversationId(user1, user2);

        return chatRepository
                .findByConversationIdOrderByCreatedAtAsc(conversationId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ======================================================
    // GET CHAT HISTORY
    // ======================================================
    @Override
    @Transactional(readOnly = true)
    public List<ChatHistoryDTO> getChatHistory(Long userId) {

        List<ChatMessage> messages =
                chatRepository.findBySenderIdOrRecipientId(userId, userId);

        return messages.stream()
                .collect(Collectors.groupingBy(ChatMessage::getConversationId))
                .values()
                .stream()
                .map(list -> list.stream()
                        .max(Comparator.comparing(ChatMessage::getCreatedAt))
                        .orElseThrow())
                .map(msg -> {

                    Long otherUserId =
                            msg.getSender().getId().equals(userId)
                                    ? msg.getRecipient().getId()
                                    : msg.getSender().getId();

                    return new ChatHistoryDTO(
                            otherUserId,
                            null, // you can enrich later with name if needed
                            msg.getMessage(),
                            msg.isSeen()
                    );
                })
                .toList();
    }

    // ======================================================
    // MARK AS SEEN
    // ======================================================
//    @Override
//    public void markAsSeen(Long messageId) {
//
//        ChatMessage message = chatRepository.findById(messageId)
//                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
//
//        message.setSeen(true);
//    }


    @Override
    public void markAsSeen(Long messageId) {

        Long currentUserId = SecurityUtils.getCurrentUserId();

        ChatMessage message = chatRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));

        if (!message.getRecipient().getId().equals(currentUserId)) {
            throw new ForbiddenOperationException("You cannot mark this message");
        }

        message.setSeen(true);
    }


    // ======================================================
    // HELPER - Conversation ID
    // ======================================================
    private String buildConversationId(Long user1, Long user2) {
        return user1 < user2
                ? user1 + "_" + user2
                : user2 + "_" + user1;
    }

    // ======================================================
    // 🔹 MANUAL MAPPER
    // ======================================================
    private ChatMessageResponseDTO toResponseDTO(ChatMessage message) {

        return new ChatMessageResponseDTO(
                message.getId(),
                message.getSender().getId(),
                message.getRecipient().getId(),
                message.getMessage(),
                message.getCreatedAt(),
                message.isSeen()
        );
    }
}
