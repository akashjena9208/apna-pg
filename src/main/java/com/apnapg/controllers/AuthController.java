package com.apnapg.controllers;

import com.apnapg.dto.LoginRequest;
import com.apnapg.dto.LoginResponse;
import com.apnapg.entity.RefreshToken;
import com.apnapg.entity.User;
import com.apnapg.exception.ApiSuccessResponse;
import com.apnapg.repositories.UserRepository;
import com.apnapg.security.CustomUserDetails;
import com.apnapg.security.JwtUtil;
import com.apnapg.security.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

//    @PostMapping("/login")
//    public LoginResponse login(
//            @Valid @RequestBody LoginRequest request,
//            HttpServletResponse response
//    ) {
//
//        Authentication authentication =
//                authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                request.email(), request.password()
//                        )
//                );
//
//        CustomUserDetails userDetails =
//                (CustomUserDetails) authentication.getPrincipal();
//
//        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
//
//        String accessToken = jwtUtil.generateAccessToken(userDetails);
//        String refreshToken = jwtUtil.generateRefreshToken(user);
//
//        refreshTokenService.create(user, refreshToken);
//
//        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
//                .httpOnly(true)
//                .secure(false) // true in prod
//                .path("/api/auth")
//                .maxAge(7 * 24 * 60 * 60)
//                .build();
//
//        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
//
//        return new LoginResponse(
//                accessToken,
//                userDetails.getRole(),
//                userDetails.getUsername()
//        );
//    }

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(), request.password()
                        )
                );

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        refreshTokenService.create(user, refreshToken);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false) // true in prod
                .path("/api/auth")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        LoginResponse loginResponse = new LoginResponse(
                accessToken,
                userDetails.getRole(),
                userDetails.getUsername()
        );

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(200, "Login successful", loginResponse)
        );
    }


//    @PostMapping("/refresh")
//    public Map<String, String> refresh(
//            @CookieValue("refreshToken") String refreshToken
//    ) {
//        RefreshToken rt = refreshTokenService.validate(refreshToken);
//
//        CustomUserDetails cud =
//                new CustomUserDetails(rt.getUser());
//
//        String newAccessToken = jwtUtil.generateAccessToken(cud);
//
//        return Map.of("accessToken", newAccessToken);
//    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiSuccessResponse<Map<String, String>>> refresh(
            @CookieValue("refreshToken") String refreshToken
    ) {
        RefreshToken rt = refreshTokenService.validate(refreshToken);

        CustomUserDetails cud = new CustomUserDetails(rt.getUser());
        String newAccessToken = jwtUtil.generateAccessToken(cud);

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(200, "Token refreshed",
                        Map.of("accessToken", newAccessToken))
        );
    }


//
//    @PostMapping("/logout")

    /// /    public void logout(
    /// /            @CookieValue("refreshToken") String refreshToken,
    /// /            HttpServletResponse response
    /// /    ) {
    /// /        refreshTokenService.revoke(refreshToken);
    /// /
    /// /        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
    /// /                .path("/api/auth")
    /// /                .maxAge(0)
    /// /                .build();
    /// /
    /// /        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
    /// /    }


    @PostMapping("/logout")
    public ResponseEntity<ApiSuccessResponse<Void>> logout(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        if (refreshToken != null) {
            refreshTokenService.revoke(refreshToken);
        }

        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/api/auth")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(200, "Logged out successfully", null)
        );
    }


}

