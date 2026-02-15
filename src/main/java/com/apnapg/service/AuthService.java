////package com.apnapg.service;
////import com.apnapg.dto.auth.LoginRequest;
////import com.apnapg.dto.auth.LoginResponse;
////import com.apnapg.dto.auth.RefreshTokenResponse;
////
////public interface AuthService {
////
////    LoginResponse login(LoginRequest request);
////
////    RefreshTokenResponse refreshToken(String refreshToken);
////
////    void logout(Long userId);
////
////    void validateUserStatus(Long userId);
////
////    void linkOAuthUser(String email, String provider);
////}
//package com.apnapg.service;
//
//import com.apnapg.dto.auth.*;
//
//public interface AuthService {
//
//    LoginResponse login(LoginRequest request);
//
//    RefreshTokenResult refreshToken(String refreshToken);
//
//    void logout(String refreshToken);
//}
package com.apnapg.service;

import com.apnapg.dto.auth.LoginRequest;
import com.apnapg.dto.auth.LoginResponse;
import com.apnapg.dto.auth.RefreshTokenResponse;
import com.apnapg.entity.User;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    RefreshTokenResponse refreshToken(String rawRefreshToken);

    void logout(String rawRefreshToken);

//void unlockIfLockExpired(User user);

}
