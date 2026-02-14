package com.apnapg.controllers;

import com.apnapg.dto.api.ApiResponse;
import com.apnapg.dto.complaint.*;
import com.apnapg.security.CustomUserDetails;
import com.apnapg.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    // ======================================================
    // TENANT CREATE COMPLAINT
    // ======================================================
    @PreAuthorize("hasRole('TENANT')")
    @PostMapping
    public ApiResponse<ComplaintResponseDTO> createComplaint(
            @Valid @RequestBody ComplaintCreateDTO dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        ComplaintResponseDTO response =
                complaintService.createComplaint(dto, userDetails.getTenantId());

        return ApiResponse.success(response, "Complaint created");
    }

    // ======================================================
    // OWNER UPDATE STATUS
    // ======================================================
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{id}/status")
    public ApiResponse<String> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody ComplaintStatusUpdateDTO dto
    ) {

        complaintService.updateStatus(id, dto);

        return ApiResponse.success("Status updated", "Success");
    }

    // ======================================================
    // TENANT VIEW OWN COMPLAINTS
    // ======================================================
    @PreAuthorize("hasRole('TENANT')")
    @GetMapping("/me")
    public ApiResponse<List<ComplaintResponseDTO>> getMyComplaints(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        List<ComplaintResponseDTO> list =
                complaintService.getTenantComplaints(userDetails.getTenantId());

        return ApiResponse.success(list, "Tenant complaints fetched");
    }

    // ======================================================
    // OWNER VIEW PG COMPLAINTS
    // ======================================================
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/pg/{pgId}")
    public ApiResponse<List<ComplaintResponseDTO>> getPGComplaints(
            @PathVariable Long pgId
    ) {

        List<ComplaintResponseDTO> list =
                complaintService.getPGComplaints(pgId);

        return ApiResponse.success(list, "PG complaints fetched");
    }
}
