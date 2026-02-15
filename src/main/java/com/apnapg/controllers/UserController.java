package com.apnapg.controllers;

import com.apnapg.dto.api.ApiResponse;
import com.apnapg.dto.auth.UserResponseDTO;
import com.apnapg.entity.User;
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

    // ==========================================
    // GET CURRENT USER
    // ==========================================
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ApiResponse<UserResponseDTO> getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userService.findById(userDetails.getUserId());

        return ApiResponse.success(
                mapToDto(user),
                "User profile fetched"
        );
    }

    // ==========================================
    // ADMIN: GET USER BY ID
    // ==========================================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<UserResponseDTO> getUserById(@PathVariable Long id) {

        User user = userService.findById(id);

        return ApiResponse.success(
                mapToDto(user),
                "User fetched successfully"
        );
    }

    // ==========================================
    // ADMIN: ENABLE USER
    // ==========================================
    //@PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    @PutMapping("/{email}/enable")
    public ApiResponse<String> enableUser(@PathVariable String email) {

        userService.enableUser(email);

        return ApiResponse.success("User enabled", "Success");
    }

    // ==========================================
    // ADMIN: DISABLE USER
    // ==========================================
    //@PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    @PutMapping("/{email}/disable")
    public ApiResponse<String> disableUser(@PathVariable String email) {

        userService.disableUser(email);

        return ApiResponse.success("User disabled", "Success");
    }

    // ==========================================
    // 🔹 MANUAL MAPPER (Builder Style)
    // ==========================================
    private UserResponseDTO mapToDto(User user) {

        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .enabled(user.isEnabled())
                .accountNonLocked(user.isAccountNonLocked())
                .build();
    }
}
//.authProvider(user.getAuthProvider().name())
