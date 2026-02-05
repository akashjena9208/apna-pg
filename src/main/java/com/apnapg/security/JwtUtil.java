package com.apnapg.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // Must be at least 32 characters
    private static final String SECRET_KEY =
            "apna_pg_super_secret_key_which_is_very_secure_123";

    private static final long ACCESS_TOKEN_EXPIRY =
            15 * 60 * 1000; // 15 minutes

    // ✅ RETURN SecretKey (NOT Key)
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    // ✅ Generate Access Token
    public String generateAccessToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", userDetails.getRole())
                .claim("userId", userDetails.getUserId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY))
                .signWith(getSigningKey())
                .compact();
    }

    // ✅ Parse & validate token (JJWT 0.13.x)
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())   // ✅ now matches SecretKey
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }
}
