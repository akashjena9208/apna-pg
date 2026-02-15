package com.apnapg.dto.auth;

import java.time.Instant;

public record LoginResponse(
        String accessToken,
        String email,
        String role,
        String provider,
        Instant expiresAt
) {}
