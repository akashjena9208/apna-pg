package com.apnapg.service;

import com.apnapg.dto.ContactMessageDTO;
import com.apnapg.entity.ContactMessage;
import com.apnapg.mapper.ContactMessageMapper;
import com.apnapg.repositories.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public ContactMessageDTO submitMessage(ContactMessageDTO dto) {
        ContactMessage message = ContactMessageMapper.toEntity(dto);
        ContactMessage savedMessage = contactMessageRepository.save(message);
        return ContactMessageMapper.toDTO(savedMessage);
    }

    public List<ContactMessageDTO> getAllMessages() {
        return contactMessageRepository.findAll().stream()
                .map(ContactMessageMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ContactMessageDTO markResolved(Long id) {
        ContactMessage message = contactMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setResolved(true);
        ContactMessage savedMessage = contactMessageRepository.save(message);
        return ContactMessageMapper.toDTO(savedMessage);
    }
}
