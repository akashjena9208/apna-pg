package com.apnapg.controllers;

import com.apnapg.dto.api.ApiResponse;
import com.apnapg.dto.tenant.*;
import com.apnapg.exceptions.BadRequestException;
import com.apnapg.security.CustomUserDetails;
import com.apnapg.service.TenantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;
    private final ObjectMapper objectMapper;

    // ==========================================
    // REGISTER
    // ==========================================
//    @PostMapping("/register")
//    public ApiResponse<TenantResponseDTO> register(
//            @RequestBody TenantRegistrationDTO dto,
//            @RequestParam String aadhaarUrl) {
//
//        return ApiResponse.success(
//                tenantService.registerTenant(dto, aadhaarUrl),
//                "Tenant registered successfully"
//        );
//    }
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ApiResponse<TenantResponseDTO> registerTenant(
            @RequestPart("data") String data,
            @RequestPart("aadhaar") MultipartFile aadhaarFile
    ) throws Exception {

        if (aadhaarFile == null || aadhaarFile.isEmpty()) {
            throw new BadRequestException("Aadhaar file is required");
        }

        // 🔥 Use Spring-configured mapper
        TenantRegistrationDTO dto =
                objectMapper.readValue(data, TenantRegistrationDTO.class);

        TenantResponseDTO response =
                tenantService.registerTenant(dto, aadhaarFile);

        return ApiResponse.success(response, "Tenant registered successfully");
    }


    // ==========================================
    // GET PROFILE (Self)
    // ==========================================
    @PreAuthorize("hasRole('TENANT')")
    @GetMapping("/me")
    public ApiResponse<TenantResponseDTO> getProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ApiResponse.success(
                tenantService.getTenantProfile(userDetails.getTenantId()),
                "Profile fetched"
        );
    }

    // ==========================================
    // UPDATE PROFILE (Self)
    // ==========================================
    @PreAuthorize("hasRole('TENANT')")
    @PutMapping("/me")
    public ApiResponse<TenantResponseDTO> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody TenantUpdateDTO dto) {

        return ApiResponse.success(
                tenantService.updateTenant(userDetails.getTenantId(), dto),
                "Profile updated"
        );
    }

    // ==========================================
    // ALLOCATE ROOM (Owner only)
    // ==========================================
//    @PreAuthorize("hasRole('OWNER')")
//    @PutMapping("/{tenantId}/allocate/{roomId}")
//    public ApiResponse<String> allocateRoom(
//            @PathVariable Long tenantId,
//            @PathVariable Long roomId) {
//
//        tenantService.allocateRoom(tenantId, roomId);
//
//        return ApiResponse.success("Room allocated", "Success");
//    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{tenantId}/allocate/{roomId}")
    public ApiResponse<String> allocateRoom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long tenantId,
            @PathVariable Long roomId) {

        tenantService.allocateRoom(
                tenantId,
                roomId,
                userDetails.getOwnerId()
        );

        return ApiResponse.success("Room allocated", "Success");
    }



    // ==========================================
    // VACATE ROOM
    // ==========================================
    @PreAuthorize("hasRole('TENANT')")
    @PutMapping("/vacate")
    public ApiResponse<String> vacateRoom(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        tenantService.vacateRoom(userDetails.getTenantId());

        return ApiResponse.success("Room vacated", "Success");
    }
}
