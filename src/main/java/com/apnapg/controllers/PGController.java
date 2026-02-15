package com.apnapg.controllers;

import com.apnapg.dto.api.ApiResponse;
import com.apnapg.dto.pagination.PageResponseDTO;
import com.apnapg.dto.pg.PGCreateRequest;
import com.apnapg.dto.pg.PGResponse;
import com.apnapg.dto.pg.PGSearchDTO;
import com.apnapg.security.CustomUserDetails;
import com.apnapg.service.FileStorageService;
import com.apnapg.service.PGService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pgs")
@RequiredArgsConstructor
public class PGController {

    private final PGService pgService;
    private final FileStorageService fileStorageService;

    // ======================================================
    // CREATE PG (OWNER ONLY)
    // ======================================================
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public ApiResponse<PGResponse> createPG(
            @Valid @RequestBody PGCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        PGResponse response = pgService.createPG(
                request,
                userDetails.getOwnerId()
        );

        return ApiResponse.success(response, "PG created successfully");
    }

    // ======================================================
    // UPDATE PG (OWNER ONLY)
    // ======================================================
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{pgId}")
    public ApiResponse<PGResponse> updatePG(
            @PathVariable Long pgId,
            @Valid @RequestBody PGCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        PGResponse response = pgService.updatePG(
                pgId,
                request,
                userDetails.getOwnerId()
        );

        return ApiResponse.success(response, "PG updated successfully");
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/{pgId}/images")
    public ApiResponse<String> uploadPGImage(
            @PathVariable Long pgId,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        String fileName = fileStorageService.store(file);

        pgService.addImageToPG(pgId, userDetails.getOwnerId(), fileName);

        return ApiResponse.success(fileName, "Image uploaded successfully");
    }


    // ======================================================
    // DELETE PG (OWNER ONLY)
    // ======================================================
    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{pgId}")
    public ApiResponse<String> deletePG(
            @PathVariable Long pgId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        pgService.deletePG(pgId, userDetails.getOwnerId());

        return ApiResponse.success("PG deleted", "Success");
    }

    // ======================================================
    // GET SINGLE PG (PUBLIC)
    // ======================================================
    @GetMapping("/{pgId}")
    public ApiResponse<PGResponse> getPG(@PathVariable Long pgId) {

        PGResponse response = pgService.getPG(pgId);

        return ApiResponse.success(response, "PG fetched successfully");
    }

    // ======================================================
    // SEARCH PGs (PUBLIC + PAGINATED)
    // ======================================================
    @GetMapping("/search")
    public ApiResponse<PageResponseDTO<PGResponse>> searchPGs(
            PGSearchDTO search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponseDTO<PGResponse> result =
                pgService.searchPGs(search, page, size);

        return ApiResponse.success(result, "PG search completed");
    }
}
