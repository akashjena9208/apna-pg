
package com.apnapg.service.impl;

import com.apnapg.dto.review.*;
import com.apnapg.entity.PG;
import com.apnapg.entity.Review;
import com.apnapg.entity.Tenant;
import com.apnapg.exceptions.ConflictException;
import com.apnapg.exceptions.ForbiddenOperationException;
import com.apnapg.exceptions.ResourceNotFoundException;
import com.apnapg.repository.PGRepository;
import com.apnapg.repository.ReviewRepository;
import com.apnapg.repository.TenantRepository;
import com.apnapg.security.SecurityUtils;
import com.apnapg.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//@Transactional
//public class ReviewServiceImpl implements ReviewService {
//
//    private final ReviewRepository reviewRepository;
//    private final PGRepository pgRepository;
//    private final TenantRepository tenantRepository;
//    private final ReviewMapper reviewMapper;
//
//    // ======================================================
//    // CREATE REVIEW (TENANT ONLY)
//    // ======================================================
//    @Override
//    public ReviewResponseDTO createReview(ReviewDTO dto) {
//
//        Long userId = SecurityUtils.getCurrentUserId();
//
//        Tenant tenant = tenantRepository.findByUserId(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
//
//        PG pg = pgRepository.findById(dto.pgId())
//                .orElseThrow(() -> new ResourceNotFoundException("PG not found"));
//
//        reviewRepository.findByTenant_IdAndPg_Id(tenant.getId(), dto.pgId())
//                .ifPresent(r -> {
//                    throw new ConflictException("You already reviewed this PG");
//                });
//
//        Review review = reviewMapper.toEntity(dto);
//        review.setTenant(tenant);
//        review.setPg(pg);
//
//        reviewRepository.save(review);
//
//        log.info("Review created by tenant {} for PG {}", tenant.getId(), dto.pgId());
//
//        return reviewMapper.toResponseDTO(review);
//    }
//
//    // ======================================================
//    // UPDATE REVIEW (OWNER OF REVIEW ONLY)
//    // ======================================================
//    @Override
//    public ReviewResponseDTO updateReview(Long reviewId, ReviewDTO dto) {
//
//        Long userId = SecurityUtils.getCurrentUserId();
//
//        Review review = reviewRepository.findById(reviewId)
//                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
//
//        if (!review.getTenant().getUser().getId().equals(userId)) {
//            throw new ForbiddenOperationException("You cannot update this review");
//        }
//
//        review.setRating(dto.rating());
//        review.setComment(dto.comment());
//
//        log.info("Review {} updated", reviewId);
//
//        return reviewMapper.toResponseDTO(review);
//    }
//
//    // ======================================================
//    // DELETE REVIEW (OWNER ONLY)
//    // ======================================================
//    @Override
//    public void deleteReview(Long reviewId) {
//
//        Long userId = SecurityUtils.getCurrentUserId();
//
//        Review review = reviewRepository.findById(reviewId)
//                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
//
//        if (!review.getTenant().getUser().getId().equals(userId)) {
//            throw new ForbiddenOperationException("You cannot delete this review");
//        }
//
//        reviewRepository.delete(review);
//
//        log.info("Review {} deleted", reviewId);
//    }
//
//    // ======================================================
//    // GET REVIEWS BY PG
//    // ======================================================
//    @Override
//    @Transactional(readOnly = true)
//    public List<ReviewResponseDTO> getReviewsByPG(Long pgId) {
//
//        if (!pgRepository.existsById(pgId)) {
//            throw new ResourceNotFoundException("PG not found");
//        }
//
//        return reviewRepository.findByPg_Id(pgId)
//                .stream()
//                .map(reviewMapper::toResponseDTO)
//                .toList();
//    }
//}


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PGRepository pgRepository;
    private final TenantRepository tenantRepository;

    // ======================================================
    // CREATE REVIEW (TENANT ONLY)
    // ======================================================
    @Override
    public ReviewResponseDTO createReview(ReviewDTO dto) {

        Long userId = SecurityUtils.getCurrentUserId();

        Tenant tenant = tenantRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

        PG pg = pgRepository.findById(dto.pgId())
                .orElseThrow(() -> new ResourceNotFoundException("PG not found"));

        reviewRepository.findByTenant_IdAndPg_Id(tenant.getId(), dto.pgId())
                .ifPresent(r -> {
                    throw new ConflictException("You already reviewed this PG");
                });

        Review review = Review.builder()
                .rating(dto.rating())
                .comment(dto.comment())
                .tenant(tenant)
                .pg(pg)
                .build();

        reviewRepository.save(review);

        log.info("Review created by tenant {} for PG {}", tenant.getId(), dto.pgId());

        return toResponseDTO(review);
    }

    // ======================================================
    // UPDATE REVIEW (OWNER OF REVIEW ONLY)
    // ======================================================
    @Override
    public ReviewResponseDTO updateReview(Long reviewId, ReviewDTO dto) {

        Long userId = SecurityUtils.getCurrentUserId();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        if (!review.getTenant().getUser().getId().equals(userId)) {
            throw new ForbiddenOperationException("You cannot update this review");
        }

        review.setRating(dto.rating());
        review.setComment(dto.comment());

        log.info("Review {} updated", reviewId);

        return toResponseDTO(review);
    }

    // ======================================================
    // DELETE REVIEW (OWNER ONLY)
    // ======================================================
    @Override
    public void deleteReview(Long reviewId) {

        Long userId = SecurityUtils.getCurrentUserId();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        if (!review.getTenant().getUser().getId().equals(userId)) {
            throw new ForbiddenOperationException("You cannot delete this review");
        }

        reviewRepository.delete(review);

        log.info("Review {} deleted", reviewId);
    }

    // ======================================================
    // GET REVIEWS BY PG
    // ======================================================
    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> getReviewsByPG(Long pgId) {

        if (!pgRepository.existsById(pgId)) {
            throw new ResourceNotFoundException("PG not found");
        }

        return reviewRepository.findByPg_Id(pgId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ======================================================
    // 🔹 MANUAL MAPPER
    // ======================================================
    private ReviewResponseDTO toResponseDTO(Review review) {

        return new ReviewResponseDTO(
                review.getId(),
                review.getPg().getId(),
                review.getTenant().getId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}
