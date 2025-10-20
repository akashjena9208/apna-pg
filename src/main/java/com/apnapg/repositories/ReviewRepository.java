package com.apnapg.repositories;

import com.apnapg.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPgId(Long pgId);
}
