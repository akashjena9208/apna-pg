package com.apnapg.dto.auth;

import lombok.Builder;

@Builder
public record UserResponseDTO(
        Long id,
        String email,
        String role,
        boolean enabled,
        boolean accountNonLocked
) {}

