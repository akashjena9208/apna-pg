package com.apnapg.controllers;

import com.apnapg.dto.ReviewDTO;
import com.apnapg.dto.ComplaintDTO;
import com.apnapg.dto.ComplaintStatusUpdateDTO;
import com.apnapg.entity.Review;
import com.apnapg.entity.Complaint;
import com.apnapg.service.ReviewService;
import com.apnapg.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewComplaintController {

    private final ReviewService reviewService;
    private final ComplaintService complaintService;

    // Review endpoints
    @PostMapping("/reviews")
    public ResponseEntity<Review> addReview(@Valid @RequestBody ReviewDTO dto) {
        return ResponseEntity.ok(reviewService.addReview(dto));
    }

    @GetMapping("/pgs/{pgId}/reviews")
    public ResponseEntity<List<Review>> getReviews(@PathVariable Long pgId) {
        return ResponseEntity.ok(reviewService.getReviewsByPG(pgId));
    }

    // Complaint endpoints
    @PostMapping("/complaints")
    public ResponseEntity<Complaint> createComplaint(@RequestBody ComplaintDTO dto) {
        return ResponseEntity.ok(complaintService.createComplaint(dto));
    }

    @PutMapping("/complaints/{id}/status")
    public ResponseEntity<Complaint> updateComplaintStatus(@PathVariable Long id,
                                                           @RequestBody ComplaintStatusUpdateDTO dto) {
        return ResponseEntity.ok(complaintService.updateComplaintStatus(id, dto));
    }

    @GetMapping("/pgs/{pgId}/complaints")
    public ResponseEntity<List<Complaint>> getPGComplaints(@PathVariable Long pgId) {
        return ResponseEntity.ok(complaintService.getPGComplaints(pgId));
    }

    @GetMapping("/tenants/{tenantId}/complaints")
    public ResponseEntity<List<Complaint>> getTenantComplaints(@PathVariable Long tenantId) {
        return ResponseEntity.ok(complaintService.getTenantComplaints(tenantId));
    }
}
