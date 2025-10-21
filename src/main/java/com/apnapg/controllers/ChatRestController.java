package com.apnapg.controllers;

import com.apnapg.dto.ChatMessageDTO;
import com.apnapg.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessageDTO>> getChatHistory(
            @RequestParam String email1,
            @RequestParam String email2) {
        List<ChatMessageDTO> history = chatService.getChatHistory(email1, email2);
        return ResponseEntity.ok(history);
    }
}



/*
* Description of My Chat Project Setup in VS Code

Backend (Spring Boot)

I have a Spring Boot backend that provides two main features:

REST API: To fetch chat history between two users.

Endpoint example: GET /api/chat/history?email1=...&email2=...

Returns messages in JSON format.

WebSocket/STOMP API: To support real-time chat.

WebSocket endpoint: /ws-chat

STOMP messaging endpoint: /app/sendMessage

Users subscribe to topics: /topic/messages/<email>

Messages sent via STOMP are delivered to both recipient and sender so everyone sees updates in real-time.

Backend handles message persistence using a service layer (ChatService) and DTOs (ChatMessageDTO).

Frontend in VS Code

I can use three different approaches:

Plain HTML/CSS/JavaScript:

I create a static HTML page (chat.html) with inputs for user email, recipient email, and message.

I use SockJS and STOMP.js to connect to Spring Boot WebSocket for real-time chat.

Messages are displayed as chat bubbles, with different styling for messages sent by “Me” and by others.

React frontend:

I create a React component that replicates the chat UI.

Uses sockjs-client and @stomp/stompjs to connect to backend WebSocket.

Handles state with React hooks: useState for messages, useEffect for subscribing and auto-scrolling.

Can optionally fetch chat history from REST API on component mount.

CSS/HTML/JS in VS Code can be tested directly in browser for development.

Chat UI includes message bubbles, timestamps, auto-scroll, and prevents sending messages to self.

How real-time chat works

Each user enters their email and connects to the WebSocket.

When a message is sent, it is published to /app/sendMessage.

Backend receives the message, saves it to DB, and broadcasts it to both sender and recipient topics.

Frontend listens to /topic/messages/<user-email> and displays messages instantly.

Advantages of this setup

Full real-time chat functionality without page reload.

Works with both static HTML/JS or React frontend.

Easily extendable with features like chat history, seen/unseen status, colored bubbles, and timestamps.

Testing workflow

Open two browser tabs with different emails to simulate two users.

Connect both users, send messages, and verify that messages appear correctly on both sides.

Can run frontend locally in VS Code (React dev server) while backend runs in Spring Boot (localhost:8080).

💡 Summary:
In VS Code, I can either use normal HTML/CSS/JS or a React frontend to connect to the Spring Boot backend. The backend provides REST API for history and WebSocket/STOMP for real-time chat, allowing users to send and receive messages instantly.
* */