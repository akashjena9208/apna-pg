package com.apnapg.dto.auth;
//package com.apnapg.dto.auth;
//public record UserResponseDTO(
//        Long id,
//        String email,
//        String role,
//        boolean enabled
//) {}
public record UserResponseDTO(
        Long id,
        String email,
        String role,
        boolean enabled,
        boolean accountNonLocked
) {}
