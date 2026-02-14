//package com.apnapg.controllers;
//
//import com.apnapg.dto.api.ApiResponse;
//import com.apnapg.dto.auth.*;
//import com.apnapg.entity.User;
//import com.apnapg.security.CookieUtil;
//import com.apnapg.service.AuthService;
//import com.apnapg.service.RefreshTokenService;
//import com.apnapg.service.UserService;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthService authService;
//    private final RefreshTokenService refreshTokenService;
//    private final UserService userService;
//
//    @Value("${app.jwt.refresh-expiration}")
//    private long refreshExpirationMs;
//
//    @Value("${app.cookie.secure}")
//    private boolean secureCookie;
//
//    @PostMapping("/login")
//    public ApiResponse<LoginResponse> login(
//            @Valid @RequestBody LoginRequest request,
//            HttpServletResponse response
//    ) {
//
//        LoginResponse loginResponse = authService.login(request);
//
//        User user = userService.findByEmail(request.email());
//
//        String refreshToken =
//                refreshTokenService.createRefreshToken(user);
//
//        CookieUtil.addRefreshTokenCookie(
//                response,
//                refreshToken,
//                refreshExpirationMs / 1000,
//                secureCookie
//        );
//
//        return ApiResponse.success(loginResponse, "Login successful");
//    }
//
//    @PostMapping("/refresh")
//    public ApiResponse<RefreshTokenResponse> refresh(
//            @CookieValue("refreshToken") String refreshToken,
//            HttpServletResponse response
//    ) {
//
//        RefreshTokenResponse body =
//                authService.refreshToken(refreshToken);
//
//        CookieUtil.addRefreshTokenCookie(
//                response,
//                refreshToken,
//                refreshExpirationMs / 1000,
//                secureCookie
//        );
//
//        return ApiResponse.success(body, "Token refreshed");
//    }
//
//    @PostMapping("/logout")
//    public ApiResponse<Void> logout(
//            @CookieValue(name = "refreshToken", required = false) String refreshToken,
//            HttpServletResponse response
//    ) {
//
//        if (refreshToken != null)
//            authService.logout(refreshToken);
//
//        CookieUtil.deleteRefreshTokenCookie(response, secureCookie);
//
//        return ApiResponse.success(null, "Logged out");
//    }
//}


package com.apnapg.controllers;

import com.apnapg.dto.api.ApiResponse;
import com.apnapg.dto.auth.*;
import com.apnapg.entity.User;
import com.apnapg.security.CookieUtil;
import com.apnapg.service.AuthService;
import com.apnapg.service.RefreshTokenService;
import com.apnapg.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @Value("${app.jwt.refresh-expiration}")
    private long refreshExpirationMs;

    @Value("${app.cookie.secure}")
    private boolean secureCookie;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {

        LoginResponse loginResponse = authService.login(request);

        User user = userService.findByEmail(request.email());

        String refreshToken = refreshTokenService.createRefreshToken(user);

        CookieUtil.addRefreshTokenCookie(
                response,
                refreshToken,
                refreshExpirationMs / 1000,
                secureCookie
        );

        return ApiResponse.success(loginResponse, "Login successful");
    }

    @PostMapping("/refresh")
    public ApiResponse<RefreshTokenResponse> refresh(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {

        // Rotate token properly
        var rotatedToken = refreshTokenService.rotateRefreshToken(refreshToken);

        String newRawToken = refreshTokenService.createRefreshToken(rotatedToken.getUser());

        RefreshTokenResponse body =
                authService.refreshToken(refreshToken);

        CookieUtil.addRefreshTokenCookie(
                response,
                newRawToken,
                refreshExpirationMs / 1000,
                secureCookie
        );

        return ApiResponse.success(body, "Token refreshed");
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {

        if (refreshToken != null)
            authService.logout(refreshToken);

        CookieUtil.deleteRefreshTokenCookie(response, secureCookie);

        return ApiResponse.success(null, "Logged out");
    }
}
