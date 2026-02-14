package com.apnapg.repository;

import com.apnapg.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPg_Id(Long pgId);

    Optional<Review> findByTenant_IdAndPg_Id(Long tenantId, Long pgId);
}
