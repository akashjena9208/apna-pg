package com.apnapg.repository;

import com.apnapg.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // Find token by hash (never store raw token)
    Optional<RefreshToken> findByTokenHash(String tokenHash);

    // Revoke all tokens of a user (logout everywhere)
    @Modifying
    @Transactional
    @Query("""
        UPDATE RefreshToken r
        SET r.revoked = true
        WHERE r.user.email = :email
    """)
    void revokeAllByUserEmail(String email);

    // Optional: Cleanup expired tokens (good for scheduler)
    @Modifying
    @Transactional
    @Query("""
        DELETE FROM RefreshToken r
        WHERE r.expiryDate < CURRENT_TIMESTAMP
    """)
    void deleteExpiredTokens();
}
