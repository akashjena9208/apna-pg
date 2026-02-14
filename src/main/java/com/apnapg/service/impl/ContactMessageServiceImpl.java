package com.apnapg.service.impl;

import com.apnapg.dto.contact.*;
import com.apnapg.entity.ContactMessage;
import com.apnapg.exceptions.ResourceNotFoundException;
import com.apnapg.mappers.ContactMessageMapper;
import com.apnapg.repository.ContactMessageRepository;
import com.apnapg.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//@Transactional
//public class ContactMessageServiceImpl implements ContactMessageService {
//
//    private final ContactMessageRepository contactRepository;
//    private final ContactMessageMapper contactMapper;
//
//    // ======================================================
//    // SUBMIT MESSAGE (Public)
//    // ======================================================
//    @Override
//    public ContactMessageResponseDTO submitMessage(ContactMessageDTO dto) {
//
//        ContactMessage message = contactMapper.toEntity(dto);
//        message.setResolved(false);
//
//        contactRepository.save(message);
//
//        log.info("New contact message submitted from {}", dto.email());
//
//        return contactMapper.toResponseDTO(message);
//    }
//
//    // ======================================================
//    // MARK AS RESOLVED (Admin Only)
//    // ======================================================
//    @Override
//    public void markResolved(Long messageId) {
//
//        ContactMessage message = contactRepository.findById(messageId)
//                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
//
//        if (message.isResolved()) {
//            log.warn("Message {} already resolved", messageId);
//            return;
//        }
//
//        message.setResolved(true);
//
//        log.info("Contact message {} marked as resolved", messageId);
//    }
//
//    // ======================================================
//    // GET ALL MESSAGES (Admin Only)
//    // ======================================================
//    @Override
//    @Transactional(readOnly = true)
//    public List<ContactMessageResponseDTO> getAllMessages() {
//
//        return contactRepository.findAll()
//                .stream()
//                .map(contactMapper::toResponseDTO)
//                .toList();
//    }
//}
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository contactRepository;

    // ======================================================
    // SUBMIT MESSAGE (Public)
    // ======================================================
    @Override
    public ContactMessageResponseDTO submitMessage(ContactMessageDTO dto) {

        ContactMessage message = ContactMessage.builder()
                .name(dto.name())
                .email(dto.email())
                .subject(dto.subject())
                .message(dto.message())
                .resolved(false)
                .build();

        contactRepository.save(message);

        log.info("New contact message submitted from {}", dto.email());

        return toResponseDTO(message);
    }

    // ======================================================
    // MARK AS RESOLVED (Admin Only)
    // ======================================================
    @Override
    public void markResolved(Long messageId) {

        ContactMessage message = contactRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));

        if (message.isResolved()) {
            log.warn("Message {} already resolved", messageId);
            return;
        }

        message.setResolved(true);

        log.info("Contact message {} marked as resolved", messageId);
    }

    // ======================================================
    // GET ALL MESSAGES (Admin Only)
    // ======================================================
    @Override
    @Transactional(readOnly = true)
    public List<ContactMessageResponseDTO> getAllMessages() {

        return contactRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ======================================================
    // 🔹 MANUAL MAPPER
    // ======================================================
    private ContactMessageResponseDTO toResponseDTO(ContactMessage message) {

        return new ContactMessageResponseDTO(
                message.getId(),
                message.getName(),
                message.getEmail(),
                message.getSubject(),
                message.getMessage(),
                message.isResolved(),
                message.getCreatedAt()
        );
    }
}
