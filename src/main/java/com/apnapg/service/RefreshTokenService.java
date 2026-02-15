////////////package com.apnapg.service;
////////////import com.apnapg.entity.User;
////////////
////////////
////////////public interface RefreshTokenService {
////////////
////////////    String createRefreshToken(User user);
////////////
////////////    void validateRefreshToken(String rawToken);
////////////
////////////    void revokeUserTokens(String email);
////////////
////////////    void deleteExpiredTokens();
////////////}
//////////package com.apnapg.service;
//////////
//////////import com.apnapg.entity.RefreshToken;
//////////import com.apnapg.entity.User;
//////////
//////////public interface RefreshTokenService {
//////////
//////////    // Create new refresh token (returns RAW token for cookie)
//////////    String createRefreshToken(User user);
//////////
//////////    // Validate and return token entity
//////////    RefreshToken validateRefreshToken(String rawToken);
//////////
//////////    // Rotate token (delete old + create new)
//////////    String rotateRefreshToken(String oldRawToken);
//////////
//////////    // Revoke all tokens of user
//////////    void revokeUserTokens(String email);
//////////
//////////    void deleteExpiredTokens();
//////////}
////////package com.apnapg.service;
////////
////////import com.apnapg.entity.User;
////////
////////public interface RefreshTokenService {
////////
////////    String createRefreshToken(User user);
////////
////////    String rotateRefreshToken(String oldRawToken);
////////
////////    void revokeToken(String rawToken);
////////
////////    void revokeUserTokens(String email);
////////
////////    void deleteExpiredTokens();
////////}
//////package com.apnapg.service;
//////
//////import com.apnapg.entity.RefreshToken;
//////import com.apnapg.entity.User;
//////
//////public interface RefreshTokenService {
//////
//////    // Create new refresh token (returns RAW token)
//////    String createRefreshToken(User user);
//////
//////    // Rotate token and return NEW entity (atomic)
//////    RefreshToken rotateRefreshToken(String oldRawToken);
//////
//////    // Revoke single token
//////    void revokeToken(String rawToken);
//////
//////    // Revoke all tokens for user (reuse detection)
//////    void revokeUserTokens(String email);
//////
//////    void deleteExpiredTokens();
//////}
////package com.apnapg.service;
////
////import com.apnapg.entity.RefreshToken;
////import com.apnapg.entity.User;
////
////public interface RefreshTokenService {
////
////    String createRefreshToken(User user);
////
////    RefreshToken rotateRefreshToken(String oldRawToken);
////
////    void revokeToken(String rawToken);
////
////    void revokeUserTokens(String email);
////
////    void deleteExpiredTokens();
////}
//package com.apnapg.service;
//
//import com.apnapg.entity.RefreshToken;
//import com.apnapg.entity.User;
//
//public interface RefreshTokenService {
//
//    String createRefreshToken(User user);
//
//    RefreshToken rotateRefreshToken(String oldRawToken);
//
//    void revokeToken(String rawToken);
//
//    void revokeUserTokens(String email);
//
//    void deleteExpiredTokens();
//}

package com.apnapg.service;

import com.apnapg.entity.RefreshToken;
import com.apnapg.entity.User;

public interface RefreshTokenService {

    String createRefreshToken(User user);

    RefreshToken rotateRefreshToken(String oldRawToken);

    void revokeToken(String rawToken);

    void revokeAllByUserEmail(String email);

    void deleteExpiredTokens();
}
