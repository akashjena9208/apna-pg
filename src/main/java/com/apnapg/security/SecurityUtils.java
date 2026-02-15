package com.apnapg.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static CustomUserDetails getCurrentUserDetails() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new IllegalStateException("No authenticated user found");
        }

        return userDetails;
    }

    public static Long getCurrentUserId() {
        return getCurrentUserDetails().getUserId();
    }

    public static String getCurrentUserRole() {
        return getCurrentUserDetails().getRole();
    }
}
