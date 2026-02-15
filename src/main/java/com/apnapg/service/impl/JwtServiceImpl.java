////package com.apnapg.service.impl;
////import com.apnapg.entity.User;
////import com.apnapg.service.JwtService;
////import io.jsonwebtoken.*;
////import io.jsonwebtoken.security.Keys;
////import jakarta.annotation.PostConstruct;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.stereotype.Service;
////import javax.crypto.SecretKey;
////import java.time.Instant;
////import java.util.Date;
////
//////@Slf4j
//////@Service
//////public class JwtServiceImpl implements JwtService {
//////
//////    @Value("${app.jwt.secret}")
//////    private String jwtSecret;
//////
//////    @Value("${app.jwt.expiration}")
//////    private long jwtExpirationMs;
//////
//////    private Key key;
//////
//////    @PostConstruct
//////    public void init() {
//////        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
//////    }
//////
//////    // ======================================================
//////    // GENERATE ACCESS TOKEN
//////    // ======================================================
//////    @Override
//////    public String generateAccessToken(User user) {
//////
//////        Instant now = Instant.now();
//////        Instant expiry = now.plusMillis(jwtExpirationMs);
//////
//////        return Jwts.builder()
//////                .setSubject(user.getEmail())
//////                .claim("role", user.getRole().name())
//////                .claim("provider", user.getAuthProvider().name())
//////                .setIssuedAt(Date.from(now))
//////                .setExpiration(Date.from(expiry))
//////                .signWith(key, SignatureAlgorithm.HS256)
//////                .compact();
//////    }
//////
//////    // ======================================================
//////    // EXTRACT EMAIL
//////    // ======================================================
//////    @Override
//////    public String extractEmail(String token) {
//////        return parseClaims(token).getSubject();
//////    }
//////
//////    // ======================================================
//////    // EXTRACT ROLE
//////    // ======================================================
//////    @Override
//////    public String extractRole(String token) {
//////        return parseClaims(token).get("role", String.class);
//////    }
//////
//////    // ======================================================
//////    // GET EXPIRY
//////    // ======================================================
//////    @Override
//////    public Instant getAccessTokenExpiry(String token) {
//////        Date expiration = parseClaims(token).getExpiration();
//////        return expiration.toInstant();
//////    }
//////
//////    // ======================================================
//////    // VALIDATE TOKEN
//////    // ======================================================
//////    @Override
//////    public boolean validateToken(String token, String email) {
//////        final String extractedEmail = extractEmail(token);
//////        return extractedEmail.equals(email) && !isTokenExpired(token);
//////    }
//////
//////    // ======================================================
//////    // INTERNAL HELPERS
//////    // ======================================================
//////    private boolean isTokenExpired(String token) {
//////        return parseClaims(token).getExpiration().before(new Date());
//////    }
//////
//////    private Claims parseClaims(String token) {
//////
//////        try {
//////            return Jwts.parserBuilder()
//////                    .setSigningKey(key)
//////                    .build()
//////                    .parseClaimsJws(token)
//////                    .getBody();
//////        } catch (ExpiredJwtException ex) {
//////            log.warn("JWT expired: {}", ex.getMessage());
//////            throw ex;
//////        } catch (JwtException | IllegalArgumentException ex) {
//////            log.error("Invalid JWT: {}", ex.getMessage());
//////            throw new RuntimeException("Invalid JWT token");
//////        }
//////    }
//////}
////@Slf4j
////@Service
////public class JwtServiceImpl implements JwtService {
////
////    @Value("${app.jwt.secret}")
////    private String jwtSecret;
////
////    @Value("${app.jwt.expiration}")
////    private long jwtExpirationMs;
////
////    private SecretKey key;
////
////    @PostConstruct
////    public void init() {
////        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
////    }
////
////    @Override
////    public String generateAccessToken(User user) {
////
////        Instant now = Instant.now();
////        Instant expiry = now.plusMillis(jwtExpirationMs);
////
////        return Jwts.builder()
////                .subject(user.getEmail())
////                .claim("role", user.getRole().name())
////                .claim("provider", user.getAuthProvider().name())
////                .issuedAt(Date.from(now))
////                .expiration(Date.from(expiry))
////                .signWith(key)
////                .compact();
////    }
////
////    @Override
////    public String extractEmail(String token) {
////        return parseClaims(token).getSubject();
////    }
////
////    @Override
////    public String extractRole(String token) {
////        return parseClaims(token).get("role", String.class);
////    }
////
////    @Override
////    public Instant getAccessTokenExpiry(String token) {
////        return parseClaims(token).getExpiration().toInstant();
////    }
////
////    @Override
////    public boolean validateToken(String token, String email) {
////        return extractEmail(token).equals(email) && !isTokenExpired(token);
////    }
////
////    private boolean isTokenExpired(String token) {
////        return parseClaims(token).getExpiration().before(new Date());
////    }
////
////    private Claims parseClaims(String token) {
////        return Jwts.parser()
////                .verifyWith(key)
////                .build()
////                .parseSignedClaims(token)
////                .getPayload();
////    }
////}
//package com.apnapg.service.impl;
//
//import com.apnapg.entity.User;
//import com.apnapg.service.JwtService;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.time.Instant;
//import java.util.Date;
//
//@Service
//@Slf4j
//public class JwtServiceImpl implements JwtService {
//
//    @Value("${app.jwt.secret}")
//    private String secret;
//
//    @Value("${app.jwt.access-expiration}")
//    private long accessExpiration;
//
//    @Value("${app.jwt.refresh-expiration}")
//    private long refreshExpiration;
//
//    private SecretKey key;
//
//    @PostConstruct
//    public void init() {
//        this.key = Keys.hmacShaKeyFor(secret.getBytes());
//    }
//
//    // ======================================================
//    // ACCESS TOKEN
//    // ======================================================
//    @Override
//    public String generateAccessToken(User user) {
//
//        Instant now = Instant.now();
//        Instant expiry = now.plusMillis(accessExpiration);
//
//        return Jwts.builder()
//                .subject(user.getEmail())
//                .claim("role", user.getRole().name())
//                .claim("provider", user.getAuthProvider().name())
//                .issuedAt(Date.from(now))
//                .expiration(Date.from(expiry))
//                .signWith(key)
//                .compact();
//    }
//
//    // ======================================================
//    // REFRESH TOKEN
//    // ======================================================
//    @Override
//    public String generateRefreshToken(User user) {
//
//        Instant now = Instant.now();
//        Instant expiry = now.plusMillis(refreshExpiration);
//
//        return Jwts.builder()
//                .subject(user.getEmail())
//                .issuedAt(Date.from(now))
//                .expiration(Date.from(expiry))
//                .signWith(key)
//                .compact();
//    }
//
//    // ======================================================
//    // EXTRACT EMAIL
//    // ======================================================
//    @Override
//    public String extractEmail(String token) {
//        return parseClaims(token).getSubject();
//    }
//
//    // ======================================================
//    // EXTRACT ROLE
//    // ======================================================
//    @Override
//    public String extractRole(String token) {
//        return parseClaims(token).get("role", String.class);
//    }
//
//    @Override
//    public Instant getAccessTokenExpiry(String token) {
//        return null;
//    }
//
//    // ======================================================
//    // GET ACCESS TOKEN EXPIRY
//    // ======================================================
//    @Override
//    public Instant getAccessTokenExpiry() {
//        return Instant.now().plusMillis(accessExpiration);
//    }
//
//    // ======================================================
//    // VALIDATE TOKEN
//    // ======================================================
//    @Override
//    public boolean validateToken(String token, String email) {
//        return extractEmail(token).equals(email) && !isExpired(token);
//    }
//
//    private boolean isExpired(String token) {
//        return parseClaims(token)
//                .getExpiration()
//                .before(new Date());
//    }
//
//    // ======================================================
//    // INTERNAL CLAIM PARSER (0.13.0 SYNTAX)
//    // ======================================================
//    private Claims parseClaims(String token) {
//
//        return Jwts.parser()
//                .verifyWith(key)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//}
package com.apnapg.service.impl;

import com.apnapg.entity.User;
import com.apnapg.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.access-expiration}")
    private long accessExpirationMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public String generateAccessToken(User user) {

        Instant now = Instant.now();
        Instant expiry = now.plusMillis(accessExpirationMs);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .claim("provider", user.getAuthProvider().name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(key)
                .compact();
    }

    @Override
    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    @Override
    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    @Override
    public Instant getAccessTokenExpiry(String token) {
        return parseClaims(token).getExpiration().toInstant();
    }

    @Override
    public boolean validateToken(String token, String email) {
        return extractEmail(token).equals(email)
                && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return parseClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
