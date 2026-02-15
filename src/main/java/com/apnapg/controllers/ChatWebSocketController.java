/// /package com.apnapg.controllers;
/// /
/// /
/// /import com.apnapg.servicessss.ChatService;
/// /import lombok.RequiredArgsConstructor;
/// /import org.springframework.messaging.handler.annotation.MessageMapping;
/// /import org.springframework.messaging.simp.SimpMessagingTemplate;
/// /import org.springframework.stereotype.Controller;
/// /
/// /
/// /@Controller
/// /@RequiredArgsConstructor
/// /public class ChatWebSocketController {
/// /
/// /    private final ChatService chatService;
/// /    private final SimpMessagingTemplate messagingTemplate;
/// /
/// /    @MessageMapping("/sendMessage")
/// /    public void receiveMessage(ChatMessageDTO messageDTO) {
/// /        ChatMessageDTO savedMsg = chatService.sendMessage(messageDTO);
/// /
/// /        // Send to recipient
/// /        messagingTemplate.convertAndSend(
/// /                "/topic/messages/" + savedMsg.recipientEmailId(),
/// /                savedMsg
/// /        );
/// /
/// /        // Send to sender
/// /        messagingTemplate.convertAndSend(
/// /                "/topic/messages/" + savedMsg.senderEmailId(),
/// /                savedMsg
/// /        );
/// /    }
/// /}
//package com.apnapg.controllers;
//
//import com.apnapg.dto.chat.ChatMessageResponseDTO;
//import com.apnapg.dto.chat.ChatSendMessageDTO;
//import com.apnapg.security.CustomUserDetails;
//import com.apnapg.service.ChatService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.*;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//
//import java.security.Principal;
//
//@Controller
//@RequiredArgsConstructor
//public class ChatWebSocketController {
//
//    private final ChatService chatService;
//    private final SimpMessagingTemplate messagingTemplate;
//
//    @MessageMapping("/chat.send")
//    public void sendMessage(@Payload ChatSendMessageDTO dto,
//                            Principal principal) {
//
//        Long senderId =
//                ((CustomUserDetails) ((Authentication) principal).getPrincipal())
//                        .getUserId();
//
//        ChatMessageResponseDTO saved =
//                chatService.sendMessage(senderId, dto);
//
//        // Send to recipient
//        messagingTemplate.convertAndSendToUser(
//                dto.recipientId().toString(),
//                "/queue/messages",
//                saved
//        );
//    }
//}
package com.apnapg.controllers;

import com.apnapg.dto.chat.ChatMessageResponseDTO;
import com.apnapg.dto.chat.ChatSendMessageDTO;
import com.apnapg.entity.User;
import com.apnapg.service.ChatService;
import com.apnapg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    // Client sends to: /app/chat.send
    @MessageMapping("/chat.send")
    public void sendMessage(ChatSendMessageDTO dto, Principal principal) {

        String senderEmail = principal.getName();

        User sender = userService.findByEmail(senderEmail);
        User recipient = userService.findById(dto.recipientId());

        // Save message
        ChatMessageResponseDTO saved =
                chatService.sendMessage(sender.getId(), dto);

        // Send to recipient (real-time)
        messagingTemplate.convertAndSendToUser(
                recipient.getEmail(),
                "/queue/messages",
                saved
        );

        // Optional: also send back to sender (sync UI)
        messagingTemplate.convertAndSendToUser(
                senderEmail,
                "/queue/messages",
                saved
        );
    }
}
