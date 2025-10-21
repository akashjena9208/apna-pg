package com.apnapg.controllers;


import com.apnapg.dto.ChatMessageDTO;
import com.apnapg.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendMessage")
    public void receiveMessage(ChatMessageDTO messageDTO) {
        ChatMessageDTO savedMsg = chatService.sendMessage(messageDTO);

        // Send to recipient
        messagingTemplate.convertAndSend(
                "/topic/messages/" + savedMsg.recipientEmailId(),
                savedMsg
        );

        // Send to sender
        messagingTemplate.convertAndSend(
                "/topic/messages/" + savedMsg.senderEmailId(),
                savedMsg
        );
    }
}
