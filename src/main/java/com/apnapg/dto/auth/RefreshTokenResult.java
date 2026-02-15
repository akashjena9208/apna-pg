package com.apnapg.dto.auth;



import java.time.Instant;

public record RefreshTokenResult(
        String accessToken,
        Instant expiresAt,
        String newRefreshToken
) {}
