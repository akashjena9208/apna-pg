
package com.apnapg.service.impl;

import com.apnapg.entity.RefreshToken;
import com.apnapg.entity.User;
import com.apnapg.exceptions.UnauthorizedException;
import com.apnapg.repository.RefreshTokenRepository;
import com.apnapg.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Value("${app.jwt.refresh-expiration}")
    private long refreshExpirationMs;

    @Override
    public String createRefreshToken(User user) {

        String rawToken = UUID.randomUUID().toString();
        String hash = hash(rawToken);

        RefreshToken token = RefreshToken.builder()
                .tokenHash(hash)
                .user(user)
                .expiryDate(Instant.now().plusMillis(refreshExpirationMs))
                .revoked(false)
                .build();

        repository.save(token);
        return rawToken;
    }

    @Override
    public RefreshToken rotateRefreshToken(String oldRawToken) {

        String oldHash = hash(oldRawToken);

        RefreshToken existing = repository.findByTokenHash(oldHash)
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        if (existing.isRevoked()) {
            repository.revokeAllByUserEmail(existing.getUser().getEmail());
            throw new UnauthorizedException("Refresh token reuse detected");
        }

        if (existing.getExpiryDate().isBefore(Instant.now()))
            throw new UnauthorizedException("Refresh token expired");

        existing.setRevoked(true);

        String newRaw = UUID.randomUUID().toString();
        String newHash = hash(newRaw);

        RefreshToken newToken = RefreshToken.builder()
                .tokenHash(newHash)
                .user(existing.getUser())
                .expiryDate(Instant.now().plusMillis(refreshExpirationMs))
                .revoked(false)
                .build();

        repository.save(newToken);

        return newToken;
    }

    @Override
    public void revokeToken(String rawToken) {
        repository.findByTokenHash(hash(rawToken))
                .ifPresent(token -> token.setRevoked(true));
    }

    @Override
    public void revokeAllByUserEmail(String email) {
        repository.revokeAllByUserEmail(email);
    }

    @Override
    public void deleteExpiredTokens() {
        repository.deleteExpiredTokens();
    }

    private String hash(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception e) {
            throw new IllegalStateException("Token hashing failed");
        }
    }
}
