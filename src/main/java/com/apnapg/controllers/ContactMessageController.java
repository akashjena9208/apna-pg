//package com.apnapg.controllers;
//
//import com.apnapg.dto.api.ApiResponse;
//import com.apnapg.dto.contact.ContactMessageDTO;
//import com.apnapg.dto.contact.ContactMessageResponseDTO;
//import com.apnapg.service.ContactMessageService;
//
//import jakarta.validation.Valid;
//
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/contact")
//@RequiredArgsConstructor
//public class ContactMessageController {
//
//    private final ContactMessageService contactMessageService;
//
//    // ======================================================
//    // PUBLIC: Submit Contact Message
//    // ======================================================
//    @PostMapping
//    public ApiResponse<ContactMessageResponseDTO> submitMessage(
//            @Valid @RequestBody ContactMessageDTO dto
//    ) {
//
//        ContactMessageResponseDTO response =
//                contactMessageService.submitMessage(dto);
//
//        return ApiResponse.success(
//                response,
//                "Message submitted successfully"
//        );
//    }
//
//    // ======================================================
//    // ADMIN: Get All Messages
//    // ======================================================
//    @GetMapping
//    public ApiResponse<List<ContactMessageResponseDTO>> getAllMessages() {
//
//        List<ContactMessageResponseDTO> messages =
//                contactMessageService.getAllMessages();
//
//        return ApiResponse.success(
//                messages,
//                "Messages fetched successfully"
//        );
//    }
//
//    // ======================================================
//    // ADMIN: Mark Message as Resolved
//    // ======================================================
//    @PatchMapping("/{id}/resolve")
//    public ApiResponse<Void> markResolved(
//            @PathVariable Long id
//    ) {
//
//        contactMessageService.markResolved(id);
//
//        return ApiResponse.success(
//                null,
//                "Message marked as resolved"
//        );
//    }
//
//}
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
