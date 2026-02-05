package com.apnapg.controllers;

import com.apnapg.dto.LoginRequest;
import com.apnapg.dto.LoginResponse;
import com.apnapg.security.CustomUserDetails;
import com.apnapg.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthenticationManager authenticate;
//    private final JwtUtil jwtUtil;
//
//    @PostMapping("/login")
//    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.email(), request.password()
//                )
//        );
//
//        CustomUserDetails userDetails =
//                (CustomUserDetails) authentication.getPrincipal();
//
//        String token = jwtUtil.generateAccessToken(userDetails);
//
//        return new LoginResponse(
//                token,
//                userDetails.getRole(),
//                userDetails.getUsername()
//        );
//    }
//}
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        String token = jwtUtil.generateAccessToken(userDetails);

        return new LoginResponse(
                token,
                userDetails.getRole(),
                userDetails.getUsername()
        );
    }
}
