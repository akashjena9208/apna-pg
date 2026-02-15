package com.apnapg.controllers;

import com.apnapg.dto.api.ApiResponse;
import com.apnapg.dto.contact.ContactMessageDTO;
import com.apnapg.dto.contact.ContactMessageResponseDTO;
import com.apnapg.service.ContactMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping
    public ApiResponse<ContactMessageResponseDTO> submitMessage(
            @Valid @RequestBody ContactMessageDTO dto
    ) {

        return ApiResponse.success(
                contactMessageService.submitMessage(dto),
                "Message submitted successfully"
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<ContactMessageResponseDTO>> getAllMessages() {

        return ApiResponse.success(
                contactMessageService.getAllMessages(),
                "Messages fetched successfully"
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/resolve")
    public ApiResponse<Void> markResolved(@PathVariable Long id) {

        contactMessageService.markResolved(id);

        return ApiResponse.success(null, "Message marked as resolved");
    }
}
