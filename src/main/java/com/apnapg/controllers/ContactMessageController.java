package com.apnapg.controllers;

import com.apnapg.dto.ContactMessageDTO;
import com.apnapg.service.ContactMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping("/submit")
    public ResponseEntity<ContactMessageDTO> submitMessage(@Valid @RequestBody ContactMessageDTO dto) {
        return ResponseEntity.ok(contactMessageService.submitMessage(dto));
    }


    @GetMapping("/all")
    public ResponseEntity<List<ContactMessageDTO>> getAllMessages() {
        return ResponseEntity.ok(contactMessageService.getAllMessages());
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<ContactMessageDTO> markResolved(@PathVariable Long id) {
        return ResponseEntity.ok(contactMessageService.markResolved(id));
    }
}
