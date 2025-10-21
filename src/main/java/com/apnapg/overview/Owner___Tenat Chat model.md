# Apna PG – Chat Module

## Project Overview

* The Chat module enables **real-time messaging** between tenants and owners in the Apna PG system.
* Tenants and owners can **send and receive messages instantly** using WebSocket/STOMP.
* All messages are **persisted in the database** and can be retrieved using REST API endpoints.
* The module ensures **message storage, proper topic subscription, and sender/recipient updates** using Spring Boot, DTOs, service layers, and WebSocket messaging.

---

# 🧾 Chat Backend API

## 🔑 Key Features

* Real-time messaging using WebSocket + STOMP
* Persistent storage of all chat messages
* REST API to fetch chat history between any two users
* Automatic broadcast of messages to both **sender** and **recipient**
* Handles multiple simultaneous users efficiently

---

## 📝 Notes

* WebSocket endpoint: `/ws-chat`
* STOMP endpoint for sending messages: `/app/sendMessage`
* Messages are broadcasted to topics: `/topic/messages/<email>`
* Messages include sender email, recipient email, message content, and timestamp
* Message persistence is handled via `ChatService`

---

## API Endpoints

### 1️⃣ Send Chat Message (WebSocket)

* **Destination:** `/app/sendMessage` (STOMP)
* **Message Payload Example:**

```json
{
  "senderEmailId": "tenant@example.com",
  "recipientEmailId": "owner@example.com",
  "message": "Hi, is the room available?"
}
```

* **Backend Behavior:**

    1. `ChatWebSocketController` receives the message.
    2. `ChatService` saves it in the database.
    3. Server broadcasts the message to:

        * `/topic/messages/tenant@example.com` (sender)
        * `/topic/messages/owner@example.com` (recipient)

---

### 2️⃣ Get Chat History (REST API)

* **URL:** `/api/chat/history`
* **Method:** `GET`
* **Query Parameters:**

    * `email1` → First user email
    * `email2` → Second user email

**Example Request:**

```
GET /api/chat/history?email1=tenant@example.com&email2=owner@example.com
```

**Response Example:**

```json
[
  {
    "senderEmailId": "tenant@example.com",
    "recipientEmailId": "owner@example.com",
    "message": "Hi, is the room available?",
    "timestamp": "2025-10-21T05:22:18.968+05:30",
    "seen": false
  },
  {
    "senderEmailId": "owner@example.com",
    "recipientEmailId": "tenant@example.com",
    "message": "Yes, room is available.",
    "timestamp": "2025-10-21T05:23:15.539+05:30",
    "seen": false
  }
]
```

---

## 🏗️ Backend Structure Diagram

```
Client (Tenant / Owner)
-----------------------------------------
| Connect via WebSocket (/ws-chat)      |
| Subscribe to /topic/messages/<email> |
| Send message to /app/sendMessage      |
-----------------------------------------
            |
            v
Backend (Spring Boot)
-----------------------------------------
Controller: ChatWebSocketController
-----------------------------------------
@MessageMapping("/sendMessage") → ChatMessageDTO
|
v
Service: ChatService
-----------------------------------------
1. Save message in database
2. Broadcast message to:
   - Recipient topic
   - Sender topic
-----------------------------------------
Controller: ChatRestController
-----------------------------------------
@GetMapping("/history")
@RequestParam email1, email2
|
v
Service: ChatService
-----------------------------------------
Fetch messages between email1 and email2 from database
Return List<ChatMessageDTO>
-----------------------------------------
Database: chat_messages table
-----------------------------------------
| id | sender_email_id | recipient_email_id | message | timestamp | seen |
-----------------------------------------
```

---

✅ **Summary**

* Backend handles **all messaging logic** including storage, retrieval, and real-time updates.
* Works for **any number of users** simultaneously.
* Modular design: `Controller → Service → Model → Database`.
