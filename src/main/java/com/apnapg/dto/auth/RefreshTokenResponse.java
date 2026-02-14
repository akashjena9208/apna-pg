////////package com.apnapg.dto.auth;
////////
////////import java.time.Instant;
////////
////////public record RefreshTokenResponse(
////////
////////        String accessToken,
////////
////////        String tokenType,
////////
////////        Instant expiresAt
////////
////////) {}
//////package com.apnapg.dto.auth;
//////
//////import com.fasterxml.jackson.annotation.JsonIgnore;
//////import java.time.Instant;
//////
//////public record RefreshTokenResponse(
//////        String accessToken,
//////        String tokenType,
//////        Instant expiresAt,
//////        @JsonIgnore String newRefreshToken
//////) {}
////package com.apnapg.dto.auth;
////
////import java.time.Instant;
////
////public record RefreshTokenResponse(
////        String accessToken,
////        String tokenType,
////        Instant expiresAt,
////        String refreshToken   // NEW RAW TOKEN
////) {}
//package com.apnapg.dto.auth;
//
//import java.time.Instant;
//
//public record RefreshTokenResponse(
//        String accessToken,
//        String tokenType,
//        Instant expiresAt
//) {}
package com.apnapg.dto.auth;

import java.time.Instant;

public record RefreshTokenResponse(
        String accessToken,
        String tokenType,
        Instant expiresAt
) {}
