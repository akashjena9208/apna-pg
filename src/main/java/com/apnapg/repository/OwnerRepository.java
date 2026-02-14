package com.apnapg.repository;

import com.apnapg.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    // Find owner via linked user email
    Optional<Owner> findByUserEmail(String email);

    // Check duplicate business email
    boolean existsByUserEmail(String email);

    // Phone uniqueness check
    boolean existsByPhoneNumber(String phoneNumber);
}
