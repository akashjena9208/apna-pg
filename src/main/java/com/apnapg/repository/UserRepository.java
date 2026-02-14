package com.apnapg.repository;

import com.apnapg.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailIgnoreCase(String email);


    // 🔍 Find user by email (used for login & OAuth)
    Optional<User> findByEmail(String email);

    // ✔ Check if email already exists (registration validation)
    boolean existsByEmail(String email);

    // 🔒 Increment failed login attempts
    @Modifying
    @Query("""
        UPDATE User u
        SET u.failedLoginAttempts = u.failedLoginAttempts + 1
        WHERE u.email = :email
    """)
    void incrementFailedAttempts(String email);

    // 🔓 Reset failed attempts after successful login
    @Modifying
    @Query("""
        UPDATE User u
        SET u.failedLoginAttempts = 0
        WHERE u.email = :email
    """)
    void resetFailedAttempts(String email);

    // 🚫 Lock account after max failures
    @Modifying
    @Query("""
        UPDATE User u
        SET u.accountNonLocked = false, u.lockTime = :lockTime
        WHERE u.email = :email
    """)
    void lockUser(String email, Instant lockTime);
}
