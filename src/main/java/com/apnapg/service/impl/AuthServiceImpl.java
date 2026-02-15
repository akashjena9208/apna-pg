////package com.apnapg.service.impl;
////import com.apnapg.dto.auth.LoginRequest;
////import com.apnapg.dto.auth.LoginResponse;
////import com.apnapg.dto.auth.RefreshTokenResponse;
////import com.apnapg.entity.RefreshToken;
////import com.apnapg.entity.User;
////import com.apnapg.exceptions.UnauthorizedException;
////import com.apnapg.repository.UserRepository;
////import com.apnapg.service.AuthService;
////import com.apnapg.service.JwtService;
////import com.apnapg.service.RefreshTokenService;
////import lombok.RequiredArgsConstructor;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.stereotype.Service;
////import org.springframework.transaction.annotation.Transactional;
////
////import java.time.Instant;
////
////@Service
////@RequiredArgsConstructor
////@Slf4j
////@Transactional
////public class AuthServiceImpl implements AuthService {
////
////    private final UserRepository userRepository;
////    private final JwtService jwtService;
////    private final RefreshTokenService refreshTokenService;
////    private final PasswordEncoder passwordEncoder;
////
////    @Override
////    public LoginResponse login(LoginRequest request) {
////
////        User user = userRepository.findByEmail(request.email())
////                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
////
////        if (!passwordEncoder.matches(request.password(), user.getPassword()))
////            throw new UnauthorizedException("Invalid credentials");
////
////        String accessToken = jwtService.generateAccessToken(user);
////        Instant expiry = jwtService.getAccessTokenExpiry(accessToken);
////
////        return new LoginResponse(
////                accessToken,
////                user.getEmail(),
////                user.getRole().name(),
////                user.getAuthProvider().name(),
////                expiry
////        );
////    }
////
////    @Override
////    public RefreshTokenResponse refreshToken(String rawRefreshToken) {
////
////        RefreshToken newToken =
////                refreshTokenService.rotateRefreshToken(rawRefreshToken);
////
////        User user = newToken.getUser();
////
////        String newAccessToken = jwtService.generateAccessToken(user);
////        Instant expiry = jwtService.getAccessTokenExpiry(newAccessToken);
////
////        return new RefreshTokenResponse(
////                newAccessToken,
////                "Bearer",
////                expiry
////        );
////    }
////
////    @Override
////    public void logout(String rawRefreshToken) {
////        refreshTokenService.revokeToken(rawRefreshToken);
////    }
////}
//package com.apnapg.service.impl;
//
//import com.apnapg.dto.auth.LoginRequest;
//import com.apnapg.dto.auth.LoginResponse;
//import com.apnapg.dto.auth.RefreshTokenResponse;
//import com.apnapg.entity.RefreshToken;
//import com.apnapg.entity.User;
//import com.apnapg.exceptions.UnauthorizedException;
//import com.apnapg.repository.UserRepository;
//import com.apnapg.service.AuthService;
//import com.apnapg.service.JwtService;
//import com.apnapg.service.RefreshTokenService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Instant;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//@Transactional
//public class AuthServiceImpl implements AuthService {
//
//    private final UserRepository userRepository;
//    private final JwtService jwtService;
//    private final RefreshTokenService refreshTokenService;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public LoginResponse login(LoginRequest request) {
//
//        User user = userRepository.findByEmail(request.email())
//                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
//
//        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
//            throw new UnauthorizedException("Invalid credentials");
//        }
//
//        if (!user.isEnabled()) {
//            throw new UnauthorizedException("Account disabled");
//        }
//
//        if (!user.isAccountNonLocked()) {
//            throw new UnauthorizedException("Account locked");
//        }
//
//        String accessToken = jwtService.generateAccessToken(user);
//        Instant expiry = jwtService.getAccessTokenExpiry(accessToken);
//
//        return new LoginResponse(
//                accessToken,
//                user.getEmail(),
//                user.getRole().name(),
//                user.getAuthProvider().name(),
//                expiry
//        );
//    }
//
//    @Override
//    public RefreshTokenResponse refreshToken(String rawRefreshToken) {
//
//        RefreshToken newToken =
//                refreshTokenService.rotateRefreshToken(rawRefreshToken);
//
//        User user = newToken.getUser();
//
//        String newAccessToken = jwtService.generateAccessToken(user);
//        Instant expiry = jwtService.getAccessTokenExpiry(newAccessToken);
//
//        return new RefreshTokenResponse(
//                newAccessToken,
//                "Bearer",
//                expiry
//        );
//    }
//
//    @Override
//    public void logout(String rawRefreshToken) {
//        refreshTokenService.revokeToken(rawRefreshToken);
//    }
//}

package com.apnapg.service.impl;

import com.apnapg.dto.auth.LoginRequest;
import com.apnapg.dto.auth.LoginResponse;
import com.apnapg.dto.auth.RefreshTokenResponse;
import com.apnapg.entity.RefreshToken;
import com.apnapg.entity.User;
import com.apnapg.enums.AuthProvider;
import com.apnapg.exceptions.UnauthorizedException;
import com.apnapg.repository.UserRepository;
import com.apnapg.service.AuthService;
import com.apnapg.service.JwtService;
import com.apnapg.service.RefreshTokenService;
import com.apnapg.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

   // @Override
//    public LoginResponse login(LoginRequest request) {
//
//        User user = userRepository.findByEmail(request.email())
//                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
//
//        // Auto-unlock if lock expired
//        userService.incrementFailedAttempts(user); // internally checks unlock
//
//        if (!user.isEnabled()) {
//            throw new UnauthorizedException("Account disabled");
//        }
//
//        if (!user.isAccountNonLocked()) {
//            throw new UnauthorizedException("Account locked. Try later.");
//        }
//
//        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
//
//            userService.incrementFailedAttempts(user);
//
//            throw new UnauthorizedException("Invalid credentials");
//        }
//
//        // Success → reset failed attempts
//        userService.resetFailedAttempts(user);
//
//        String accessToken = jwtService.generateAccessToken(user);
//        Instant expiry = jwtService.getAccessTokenExpiry(accessToken);
//
//        log.info("User logged in successfully: {}", user.getEmail());
//
//        return new LoginResponse(
//                accessToken,
//                user.getEmail(),
//                user.getRole().name(),
//                user.getAuthProvider().name(),
//                expiry
//        );
//    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));


        // ===============================
        // 1️⃣ Provider Check (IMPORTANT)
        // ===============================
//        if (user.getAuthProvider() != AuthProvider.LOCAL) {
//            throw new UnauthorizedException(
//                    "Please login using " + user.getAuthProvider().name()
//            );
//        }

        // 1️⃣ Check auto unlock
        if (!user.isAccountNonLocked()) {
            userService.unlockIfLockExpired(user);
        }

        if (!user.isEnabled()) {
            throw new UnauthorizedException("Account disabled");
        }

        if (!user.isAccountNonLocked()) {
            throw new UnauthorizedException("Account locked. Try later.");
        }

        // 2️⃣ Password check
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {

            userService.incrementFailedAttempts(user);

            throw new UnauthorizedException("Invalid credentials");
        }

        // 3️⃣ Success → reset attempts
        userService.resetFailedAttempts(user);

        String accessToken = jwtService.generateAccessToken(user);
        Instant expiry = jwtService.getAccessTokenExpiry(accessToken);

        return new LoginResponse(
                accessToken,
                user.getEmail(),
                user.getRole().name(),
                user.getAuthProvider().name(),
                expiry
        );
    }

    @Override
    public RefreshTokenResponse refreshToken(String rawRefreshToken) {

        RefreshToken newToken =
                refreshTokenService.rotateRefreshToken(rawRefreshToken);

        User user = newToken.getUser();

        String newAccessToken = jwtService.generateAccessToken(user);
        Instant expiry = jwtService.getAccessTokenExpiry(newAccessToken);

        return new RefreshTokenResponse(
                newAccessToken,
                "Bearer",
                expiry
        );
    }

    @Override
    public void logout(String rawRefreshToken) {
        refreshTokenService.revokeToken(rawRefreshToken);
    }
}
