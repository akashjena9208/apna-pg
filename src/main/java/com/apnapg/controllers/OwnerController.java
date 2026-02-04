package com.apnapg.controllers;

import com.apnapg.dto.*;
import com.apnapg.entity.Owner;
import com.apnapg.entity.PG;
import com.apnapg.entity.Room;
import com.apnapg.entity.Tenant;
import com.apnapg.mapper.*;
import com.apnapg.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
@Slf4j
public class OwnerController {

    private final OwnerService ownerService;
    private final PGService pgService;
    private final RoomService roomService;
    private final TenantService tenantService;

    @PostMapping("/register")
    public ResponseEntity<OwnerResponseDTO> registerOwner(
            @RequestPart("owner") @Valid OwnerRegistrationDTO ownerDTO,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ) throws Exception {

        log.info("Received registration request for email {}", ownerDTO.email());

        byte[] bytes = profileImage != null ? profileImage.getBytes() : null;
        String fileName = profileImage != null ? profileImage.getOriginalFilename() : null;

        Owner owner = ownerService.registerOwner(ownerDTO, bytes, fileName);
        OwnerResponseDTO response = OwnerMapper.toResponseDTO(owner);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/pg/create")
    public ResponseEntity<PGResponse> createPG(
            @RequestParam Long ownerId,
            @Valid @RequestPart("pg") PGCreateRequest pgRequest,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws Exception {
        PG pg = pgService.createPG(ownerId, pgRequest, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(PGMapper.toResponse(pg));
    }

    @PostMapping("/pg/{pgId}/room/add")
    public ResponseEntity<RoomResponseDTO> addRoom(
            @PathVariable Long pgId,
            @Valid @RequestBody RoomCreateDTO dto
    ) {
        Room room = roomService.addRoom(pgId, dto);
        RoomResponseDTO response = new RoomResponseDTO(
                room.getId(),
                room.getRoomNumber(),
                room.getTotalBeds(),
                room.getAvailableBeds()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/pg/list/{ownerId}")
    public ResponseEntity<List<PGResponse>> getOwnerPGs(@PathVariable Long ownerId) {
        List<PGResponse> pgList = pgService.getPGsByOwner(ownerId)
                .stream()
                .map(PGMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pgList);
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<OwnerResponseDTO> getOwnerByEmail(@PathVariable String email) {
        Owner owner = ownerService.getOwnerByEmail(email);
        OwnerResponseDTO response = OwnerMapper.toResponseDTO(owner);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tenant/{tenantId}/download-aadhaar")
    public ResponseEntity<FileSystemResource> downloadTenantAadhaar(@PathVariable Long tenantId) {
        var tenant = tenantService.getTenantById(tenantId);
        File file = new File(tenant.getAadhaarUrl());

        if (!file.exists()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(file));
    }


    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<TenantResponseDTO> getTenantById(@PathVariable Long tenantId) {
        Tenant tenant = tenantService.getTenantById(tenantId);
        TenantResponseDTO response = new TenantResponseDTO(
                tenant.getId(),
                tenant.getFirstName(),
                tenant.getLastName(),
                tenant.getEmail(),
                tenant.getPhoneNumber(),
                tenant.getAadhaarUrl()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tenants/{ownerId}")
    public ResponseEntity<List<TenantResponseDTO>> getAllTenantsByOwner(@PathVariable Long ownerId) {
        List<TenantResponseDTO> tenants = tenantService.getAllTenantsByOwnerId(ownerId)
                .stream()
                .map(t -> new TenantResponseDTO(
                        t.getId(),
                        t.getFirstName(),
                        t.getLastName(),
                        t.getEmail(),
                        t.getPhoneNumber(),
                        t.getAadhaarUrl()
                ))
                .toList();

        return ResponseEntity.ok(tenants);
    }

    @PutMapping("/tenant/{tenantId}/assign-room/{roomId}")
    public ResponseEntity<TenantResponseDTO> assignTenantToRoom(
            @PathVariable Long tenantId,
            @PathVariable Long roomId
    ) {
        Tenant tenant = tenantService.assignTenantToRoom(tenantId, roomId);

        TenantResponseDTO response = new TenantResponseDTO(
                tenant.getId(),
                tenant.getFirstName(),
                tenant.getLastName(),
                tenant.getEmail(),
                tenant.getPhoneNumber(),
                tenant.getAadhaarUrl()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{ownerId}/rooms/availability")
    public ResponseEntity<List<RoomAvailabilityDTO>> getRoomAvailability(
            @PathVariable Long ownerId) {

        return ResponseEntity.ok(
                roomService.getRoomAvailabilityByOwner(ownerId)
        );

    }
}
