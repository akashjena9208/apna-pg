package com.apnapg.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public final class CookieUtil {

    private CookieUtil() {}

    public static void addRefreshTokenCookie(
            HttpServletResponse response,
            String token,
            long maxAgeSeconds,
            boolean secure
    ) {
        Cookie cookie = new Cookie("refreshToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(secure);
        cookie.setPath("/");
        cookie.setMaxAge((int) maxAgeSeconds);
        response.addCookie(cookie);
    }

    public static void deleteRefreshTokenCookie(
            HttpServletResponse response,
            boolean secure
    ) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(secure);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
