//package com.apnapg.security;
//
//import com.apnapg.entity.RefreshToken;
//import com.apnapg.entity.User;
//import com.apnapg.repositories.RefreshTokenRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//
//@Service
//@RequiredArgsConstructor
//public class RefreshTokenService {
//
//    private final RefreshTokenRepository repository;
//
//
//    public RefreshToken create(User user, String token) {
//        repository.deleteByUser_Id(user.getId()); // rotation-safe
//
//        RefreshToken refreshToken = RefreshToken.builder()
//                .user(user)
//                .token(token)
//                .expiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60))
//                .revoked(false)
//                .build();
//
//        return repository.save(refreshToken);
//    }
//
//    public RefreshToken validate(String token) {
//        RefreshToken refreshToken = repository.findByToken(token)
//                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
//
//        if (refreshToken.isRevoked() ||
//                refreshToken.getExpiryDate().isBefore(Instant.now())) {
//            throw new RuntimeException("Refresh token expired");
//        }
//        return refreshToken;
//    }
//
//    public void revoke(String token) {
//        repository.findByToken(token).ifPresent(rt -> {
//            rt.setRevoked(true);
//            repository.save(rt);
//        });
//    }
//}

package com.apnapg.security;

import com.apnapg.entity.RefreshToken;
import com.apnapg.entity.User;
import com.apnapg.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    // ✅ REQUIRED (DELETE + SAVE)
    @Transactional
    public RefreshToken create(User user, String token) {

        // rotation-safe delete
        repository.deleteByUser_Id(user.getId());

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(token)
                .expiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60))
                .revoked(false)
                .build();

        return repository.save(refreshToken);
    }

    // ✅ read-only is fine without tx (but safe to keep)
    public RefreshToken validate(String token) {
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.isRevoked()
                || refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }

    // ✅ REQUIRED (UPDATE)
    @Transactional
    public void revoke(String token) {
        repository.findByToken(token).ifPresent(rt -> {
            rt.setRevoked(true);
            repository.save(rt);
        });
    }
}
