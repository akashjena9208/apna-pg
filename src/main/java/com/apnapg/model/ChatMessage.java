package com.apnapg.model;

public class ChatMessage {
    private String senderEmailId;
    private String recipientEmailId;
    private String message;

    // Constructors
    public ChatMessage() {}
    public ChatMessage(String senderEmailId, String recipientEmailId, String message) {
        this.senderEmailId = senderEmailId;
        this.recipientEmailId = recipientEmailId;
        this.message = message;
    }

    // Getters and Setters
    public String getSenderEmailId() { return senderEmailId; }
    public void setSenderEmailId(String senderEmailId) { this.senderEmailId = senderEmailId; }
    public String getRecipientEmailId() { return recipientEmailId; }
    public void setRecipientEmailId(String recipientEmailId) { this.recipientEmailId = recipientEmailId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
