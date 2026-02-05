package com.apnapg.dto;

public record LoginResponse(
        String accessToken,
        String role,
        String email
) {}
