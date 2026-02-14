package com.apnapg.service;

import com.apnapg.dto.contact.*;

import java.util.List;

public interface ContactMessageService {

    ContactMessageResponseDTO submitMessage(ContactMessageDTO dto);

    void markResolved(Long messageId);

    List<ContactMessageResponseDTO> getAllMessages();
}
