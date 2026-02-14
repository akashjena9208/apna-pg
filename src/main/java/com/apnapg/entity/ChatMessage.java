package com.apnapg.entity;

import com.apnapg.entity.BaseEntity;
import com.apnapg.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "chat_messages",
        indexes = {
                @Index(name = "idx_chat_conversation_time",
                        columnList = "conversationId, createdAt")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@SuperBuilder
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String conversationId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(nullable = false)
    private boolean seen = false;

    @PrePersist
    @PreUpdate
    private void validate() {
        if (sender != null && recipient != null &&
                sender.getId().equals(recipient.getId())) {
            throw new IllegalStateException("Sender and recipient cannot be the same");
        }
    }
}
