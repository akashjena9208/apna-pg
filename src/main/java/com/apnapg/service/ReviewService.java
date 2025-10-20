package com.apnapg.service;

import com.apnapg.dto.ReviewDTO;
import com.apnapg.entity.PG;
import com.apnapg.entity.Review;
import com.apnapg.entity.Tenant;
import com.apnapg.mapper.ReviewMapper;
import com.apnapg.repositories.PGRepository;
import com.apnapg.repositories.ReviewRepository;
import com.apnapg.repositories.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TenantRepository tenantRepository;
    private final PGRepository pgRepository;

    public Review addReview(ReviewDTO dto) {
        Tenant tenant = tenantRepository.findById(dto.tenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
        PG pg = pgRepository.findById(dto.pgId())
                .orElseThrow(() -> new RuntimeException("PG not found"));
        Review review = ReviewMapper.toEntity(dto, tenant, pg);
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByPG(Long pgId) {
        return reviewRepository.findByPgId(pgId);
    }
}
