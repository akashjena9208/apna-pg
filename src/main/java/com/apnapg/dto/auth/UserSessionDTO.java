package com.apnapg.dto.auth;

public record UserSessionDTO(
        Long userId,
        String email,
        String role
) {}
