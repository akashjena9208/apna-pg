package com.apnapg.controllers;

import com.apnapg.dto.api.ApiResponse;
import com.apnapg.dto.review.*;
import com.apnapg.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PreAuthorize("hasRole('TENANT')")
    @PostMapping
    public ApiResponse<ReviewResponseDTO> create(
            @Valid @RequestBody ReviewDTO dto) {

        return ApiResponse.success(
                reviewService.createReview(dto),
                "Review created"
        );
    }

    @PreAuthorize("hasRole('TENANT')")
    @PutMapping("/{id}")
    public ApiResponse<ReviewResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ReviewDTO dto) {

        return ApiResponse.success(
                reviewService.updateReview(id, dto),
                "Review updated"
        );
    }

    @PreAuthorize("hasRole('TENANT')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {

        reviewService.deleteReview(id);

        return ApiResponse.success("Review deleted", "Success");
    }

    @GetMapping("/pg/{pgId}")
    public ApiResponse<List<ReviewResponseDTO>> getByPG(
            @PathVariable Long pgId) {

        return ApiResponse.success(
                reviewService.getReviewsByPG(pgId),
                "Reviews fetched"
        );
    }
}
