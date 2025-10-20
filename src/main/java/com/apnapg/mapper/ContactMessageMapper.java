package com.apnapg.mapper;

import com.apnapg.dto.ContactMessageDTO;
import com.apnapg.entity.ContactMessage;

public class ContactMessageMapper {

    public static ContactMessage toEntity(ContactMessageDTO dto) {
        return ContactMessage.builder()
                .name(dto.name())
                .email(dto.email())
                .subject(dto.subject())
                .message(dto.message())
                .resolved(false)
                .build();
    }

    public static ContactMessageDTO toDTO(ContactMessage entity) {
        return new ContactMessageDTO(
                entity.getName(),
                entity.getEmail(),
                entity.getSubject(),
                entity.getMessage()
        );
    }
}
