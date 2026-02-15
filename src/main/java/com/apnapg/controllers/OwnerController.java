package com.apnapg.controllers;

import com.apnapg.dto.api.ApiResponse;
import com.apnapg.dto.owner.*;
import com.apnapg.security.CustomUserDetails;
import com.apnapg.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    // ===============================
    // REGISTER
    // ===============================
    @PostMapping("/register")
    public ApiResponse<OwnerResponseDTO> register(
            @RequestBody OwnerRegistrationDTO dto) {

        return ApiResponse.success(
                ownerService.registerOwner(dto),
                "Owner registered successfully"
        );
    }

    // ===============================
    // GET PROFILE (SELF)
    // ===============================
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/me")
    public ApiResponse<OwnerResponseDTO> getProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ApiResponse.success(
                ownerService.getOwnerProfile(userDetails.getOwnerId()),
                "Profile fetched"
        );
    }

    // ===============================
    // UPDATE PROFILE
    // ===============================
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/me")
    public ApiResponse<OwnerResponseDTO> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody OwnerUpdateDTO dto) {

        return ApiResponse.success(
                ownerService.updateOwner(userDetails.getOwnerId(), dto),
                "Profile updated"
        );
    }

    // ===============================
    // GET OWNER PG IDs
    // ===============================
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/me/pgs")
    public ApiResponse<?> getOwnerPGs(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ApiResponse.success(
                ownerService.getOwnerPGs(userDetails.getOwnerId()),
                "Owner PGs fetched"
        );
    }

    // ===============================
    // UPLOAD PROFILE IMAGE
    // ===============================
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/me/profile-image")
    public ApiResponse<String> uploadProfileImage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String imageUrl) {

        return ApiResponse.success(
                ownerService.uploadProfileImage(
                        userDetails.getOwnerId(), imageUrl),
                "Profile image updated"
        );
    }
}
