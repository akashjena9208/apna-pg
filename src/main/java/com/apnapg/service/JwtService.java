////package com.apnapg.service;
////import com.apnapg.entity.User;
////
////import java.time.Instant;
////
////public interface JwtService {
////
////    String generateAccessToken(User user);
////
////    String extractEmail(String token);
////
////    String extractRole(String token);
////
////    Instant getAccessTokenExpiry(String token);
////
////    boolean validateToken(String token, String email);
////}
//package com.apnapg.service;
//
//import com.apnapg.entity.User;
//import java.time.Instant;
//
//public interface JwtService {
//
//    String generateAccessToken(User user);
//
//    // ======================================================
//    // REFRESH TOKEN
//    // ======================================================
//    String generateRefreshToken(User user);
//
//    String extractEmail(String token);
//
//    String extractRole(String token);
//
//    Instant getAccessTokenExpiry(String token);   // ✅ FIXED
//
//    // ======================================================
//    // GET ACCESS TOKEN EXPIRY
//    // ======================================================
//    Instant getAccessTokenExpiry();
//
//    boolean validateToken(String token, String email);
//
//
//}
//

package com.apnapg.service;

import com.apnapg.entity.User;

import java.time.Instant;

public interface JwtService {

    String generateAccessToken(User user);

    String extractEmail(String token);

    String extractRole(String token);

    Instant getAccessTokenExpiry(String token);

    boolean validateToken(String token, String email);
}
