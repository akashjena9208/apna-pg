package com.apnapg.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String senderEmailId; // tenant or owner email

    @Column(nullable = false)
    private String recipientEmailId; // tenant or owner email

    @Column(length = 2000)
    private String message;

    private LocalDateTime timestamp;

    private Boolean seen = false;
}
