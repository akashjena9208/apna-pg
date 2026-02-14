/// /
/// /package com.apnapg.controllers;
/// /
/// /import com.apnapg.dto.api.ApiResponse;
/// /import com.apnapg.dto.auth.UserResponseDTO;
/// /import com.apnapg.entity.User;
/// /import com.apnapg.security.CustomUserDetails;
/// /import com.apnapg.service.UserService;
/// /import lombok.RequiredArgsConstructor;
/// /import org.springframework.security.access.prepost.PreAuthorize;
/// /import org.springframework.security.core.annotation.AuthenticationPrincipal;
/// /import org.springframework.web.bind.annotation.*;
/// /
/// /@RestController
/// /@RequestMapping("/api/users")
/// /@RequiredArgsConstructor
/// /public class UserController {
/// /
/// /    private final UserService userService;
/// /
/// /    // ==========================================
/// /    // GET CURRENT LOGGED-IN USER PROFILE
/// /    // ==========================================
/// /    @GetMapping("/me")
/// /    public ApiResponse<UserResponseDTO> getCurrentUser(
/// /            @AuthenticationPrincipal CustomUserDetails userDetails) {
/// /
/// /        User user = userService.findById(userDetails.getUserId());
/// /
/// /        return ApiResponse.success(user, "User profile fetched");
/// /    }
/// /
/// /    // ==========================================
/// /    // ADMIN: GET USER BY ID
/// /    // ==========================================
/// /    @PreAuthorize("hasRole('ADMIN')")
/// /    @GetMapping("/{id}")
/// /    public ApiResponse<User> getUserById(@PathVariable Long id) {
/// /
/// /        User user = userService.findById(id);
/// /
/// /        return ApiResponse.success(user, "User fetched successfully");
/// /    }
/// /
/// /    // ==========================================
/// /    // ADMIN: ENABLE USER
/// /    // ==========================================
/// /    @PreAuthorize("hasRole('ADMIN')")
/// /    @PutMapping("/{email}/enable")
/// /    public ApiResponse<String> enableUser(@PathVariable String email) {
/// /
/// /        userService.enableUser(email);
/// /
/// /        return ApiResponse.success("User enabled", "Success");
/// /    }
/// /
/// /    // ==========================================
/// /    // ADMIN: DISABLE USER
/// /    // ==========================================
/// /    @PreAuthorize("hasRole('ADMIN')")
/// /    @PutMapping("/{email}/disable")
/// /    public ApiResponse<String> disableUser(@PathVariable String email) {
/// /
/// /        userService.disableUser(email);
/// /
/// /        return ApiResponse.success("User disabled", "Success");
/// /    }
/// /}
//package com.apnapg.controllers;
//
//import com.apnapg.dto.api.ApiResponse;
//import com.apnapg.dto.auth.UserResponseDTO;
//import com.apnapg.entity.User;
//import com.apnapg.security.CustomUserDetails;
//import com.apnapg.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/users")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//
//    @GetMapping("/me")
//    public ApiResponse<UserResponseDTO> getCurrentUser(
//            @AuthenticationPrincipal CustomUserDetails userDetails) {
//
//        User user = userService.findById(userDetails.getUserId());
//
//        return ApiResponse.success(
//                mapToDto(user),
//                "User profile fetched"
//        );
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/{id}")
//    public ApiResponse<UserResponseDTO> getUserById(@PathVariable Long id) {
//
//        User user = userService.findById(id);
//
//        return ApiResponse.success(
//                mapToDto(user),
//                "User fetched successfully"
//        );
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/{email}/enable")
//    public ApiResponse<String> enableUser(@PathVariable String email) {
//
//        userService.enableUser(email);
//
//        return ApiResponse.success("User enabled", "Success");
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/{email}/disable")
//    public ApiResponse<String> disableUser(@PathVariable String email) {
//
//        userService.disableUser(email);
//
//        return ApiResponse.success("User disabled", "Success");
//    }
//
//    private UserResponseDTO mapToDto(User user) {
//        return new UserResponseDTO(
//                user.getId(),
//                user.getEmail(),
//                user.getRole().name(),
//                user.isEnabled(),
//                user.isAccountNonLocked()
//        );
//    }
//}
package com.apnapg.controllers;

import com.apnapg.dto.api.ApiResponse;

import com.apnapg.dto.auth.UserResponseDTO;
import com.apnapg.entity.User;

import com.apnapg.mappers.UserMapper;
import com.apnapg.security.CustomUserDetails;
import com.apnapg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ApiResponse<UserResponseDTO> getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userService.findById(userDetails.getUserId());

        return ApiResponse.success(
                userMapper.toDto(user),
                "User profile fetched"
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<UserResponseDTO> getUserById(@PathVariable Long id) {

        User user = userService.findById(id);

        return ApiResponse.success(
                userMapper.toDto(user),
                "User fetched successfully"
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{email}/enable")
    public ApiResponse<String> enableUser(@PathVariable String email) {

        userService.enableUser(email);

        return ApiResponse.success("User enabled", "Success");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{email}/disable")
    public ApiResponse<String> disableUser(@PathVariable String email) {

        userService.disableUser(email);

        return ApiResponse.success("User disabled", "Success");
    }
}
