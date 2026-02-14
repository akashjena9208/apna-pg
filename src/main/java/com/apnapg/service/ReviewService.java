package com.apnapg.service;

import com.apnapg.dto.review.*;

import java.util.List;

public interface ReviewService {

    ReviewResponseDTO createReview(ReviewDTO dto);

    ReviewResponseDTO updateReview(Long reviewId, ReviewDTO dto);

    void deleteReview(Long reviewId);

    List<ReviewResponseDTO> getReviewsByPG(Long pgId);
}
