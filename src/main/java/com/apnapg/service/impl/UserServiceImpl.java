package com.apnapg.service.impl;
import com.apnapg.entity.User;
import com.apnapg.exceptions.ResourceNotFoundException;
import com.apnapg.repository.UserRepository;
import com.apnapg.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCK_DURATION_MINUTES = 15;

    private final UserRepository userRepository;

    // ======================================================
    // FINDERS
    // ======================================================
    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // ======================================================
    // LOGIN FAILURE HANDLING
    // ======================================================
//    @Override
//    public void incrementFailedAttempts(User user) {
//
//        unlockIfLockExpired(user);
//
//        int attempts = user.getFailedLoginAttempts() + 1;
//        user.setFailedLoginAttempts(attempts);
//
//        if (attempts >= MAX_FAILED_ATTEMPTS) {
//            user.setAccountNonLocked(false);
//            user.setLockTime(Instant.now());
//            log.warn("User locked due to max failed attempts: {}", user.getEmail());
//        }
//
//        userRepository.save(user);
//    }
    @Override
    public void incrementFailedAttempts(User user) {

        int attempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(attempts);

        if (attempts >= MAX_FAILED_ATTEMPTS) {
            user.setAccountNonLocked(false);
            user.setLockTime(Instant.now());
            log.warn("User locked due to max failed attempts: {}", user.getEmail());
        }

        userRepository.save(user);
    }


    @Override
    public void resetFailedAttempts(User user) {
        user.setFailedLoginAttempts(0);
        user.setLockTime(null);
        userRepository.save(user);
    }

    // ======================================================
    // ACCOUNT LOCK (Manual)
    // ======================================================
    @Override
    public void lockUser(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(Instant.now());
        userRepository.save(user);
        log.warn("User account manually locked: {}", user.getEmail());
    }

    // ======================================================
    // ADMIN CONTROLS
    // ======================================================
    @Override
    public void enableUser(String email) {
        User user = findByEmail(email);
        user.setEnabled(true);
        userRepository.save(user);
        log.info("User enabled: {}", email);
    }

    @Override
    public void disableUser(String email) {
        User user = findByEmail(email);
        user.setEnabled(false);
        userRepository.save(user);
        log.warn("User disabled: {}", email);
    }

    // ======================================================
    // AUTO UNLOCK LOGIC
    // ======================================================
//    private void unlockIfLockExpired(User user) {
//
//        if (user.getLockTime() == null) return;
//
//        Instant unlockTime = user.getLockTime()
//                .plusSeconds(LOCK_DURATION_MINUTES * 60);
//
//        if (Instant.now().isAfter(unlockTime)) {
//            user.setAccountNonLocked(true);
//            user.setFailedLoginAttempts(0);
//            user.setLockTime(null);
//            log.info("User auto-unlocked: {}", user.getEmail());
//        }
//    }
    public void unlockIfLockExpired(User user) {

        if (user.getLockTime() == null) return;

        Instant unlockTime = user.getLockTime()
                .plusSeconds(LOCK_DURATION_MINUTES * 60);

        if (Instant.now().isAfter(unlockTime)) {
            user.setAccountNonLocked(true);
            user.setFailedLoginAttempts(0);
            user.setLockTime(null);
            userRepository.save(user);
            log.info("User auto-unlocked: {}", user.getEmail());
        }
    }

}
