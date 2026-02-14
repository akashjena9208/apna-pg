package com.apnapg.controllers;

import com.apnapg.dto.api.ApiResponse;
import com.apnapg.dto.chat.ChatHistoryDTO;
import com.apnapg.dto.chat.ChatMessageResponseDTO;
import com.apnapg.dto.chat.ChatSendMessageDTO;
import com.apnapg.security.CustomUserDetails;
import com.apnapg.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    // ======================================================
    // REST — GET CONVERSATION
    // ======================================================
    @GetMapping("/conversation/{otherUserId}")
    public ApiResponse<List<ChatMessageResponseDTO>> getConversation(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long otherUserId
    ) {

        List<ChatMessageResponseDTO> messages =
                chatService.getConversation(user.getUserId(), otherUserId);

        return ApiResponse.success(messages, "Conversation fetched");
    }

    // ======================================================
    // REST — GET CHAT HISTORY
    // ======================================================
    @GetMapping("/history")
    public ApiResponse<List<ChatHistoryDTO>> getChatHistory(
            @AuthenticationPrincipal CustomUserDetails user
    ) {

        List<ChatHistoryDTO> history =
                chatService.getChatHistory(user.getUserId());

        return ApiResponse.success(history, "Chat history fetched");
    }

    // ======================================================
    // REST — MARK MESSAGE AS SEEN
    // ======================================================
    @PutMapping("/seen/{messageId}")
    public ApiResponse<String> markAsSeen(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long messageId
    ) {

        chatService.markAsSeen(messageId);

        return ApiResponse.success("Marked as seen", "Success");
    }

    // ======================================================
    // WEBSOCKET — SEND MESSAGE (REAL TIME)
    // Client sends to: /app/chat.send
    // ======================================================
    //@MessageMapping("/chat.send")
    public void sendMessage(
            ChatSendMessageDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {

        ChatMessageResponseDTO response =
                chatService.sendMessage(user.getUserId(), dto);

        // Send to recipient private queue
        messagingTemplate.convertAndSendToUser(
                dto.recipientId().toString(),
                "/queue/messages",
                response
        );

        // Send back to sender (for sync)
        messagingTemplate.convertAndSendToUser(
                user.getUserId().toString(),
                "/queue/messages",
                response
        );

        log.debug("WebSocket message delivered between {} and {}",
                user.getUserId(),
                dto.recipientId());
    }
}
