package com.apnapg.mapper;

import com.apnapg.dto.ReviewDTO;
import com.apnapg.entity.PG;
import com.apnapg.entity.Review;
import com.apnapg.entity.Tenant;

public class ReviewMapper {
    public static Review toEntity(ReviewDTO dto, Tenant tenant, PG pg) {
        return Review.builder()
                .tenant(tenant)
                .pg(pg)
                .rating(dto.rating())
                .comment(dto.comment())
                .build();
    }
}
