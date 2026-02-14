package com.apnapg.dto.auth;

import jakarta.validation.constraints.NotBlank;

//public record RefreshTokenRequest(
//
//        @NotBlank(message = "Refresh token is required")
//        String refreshToken
//
//) {}


import java.time.Instant;

public record RefreshTokenResult(
        String accessToken,
        Instant expiresAt,
        String newRefreshToken
) {}
