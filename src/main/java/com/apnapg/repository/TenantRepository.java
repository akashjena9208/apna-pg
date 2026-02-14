package com.apnapg.repository;

import com.apnapg.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findByUserEmail(String email);
    Optional<Tenant> findByUserId(Long userId);
    boolean existsByUserEmail(String email);
}
