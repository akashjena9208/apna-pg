////package com.apnapg.mappers;
////
////import com.apnapg.config.CentralMapperConfig;
////import com.apnapg.dto.auth.RefreshTokenResponse;
////import com.apnapg.entity.RefreshToken;
////import org.mapstruct.*;
////
//////@Mapper(config = CentralMapperConfig.class)
//////public interface RefreshTokenMapper {
//////
//////    // ==============================
//////    // ENTITY → RESPONSE DTO
//////    // ==============================
//////    // NOTE: We DO NOT expose tokenHash or user
//////    @Mapping(target = "accessToken", ignore = true) // generated separately
//////    @Mapping(target = "tokenType", constant = "Bearer")
//////    @Mapping(source = "expiryDate", target = "expiresAt")
//////    RefreshTokenResponse toResponseDTO(RefreshToken entity);
//////
//////
//////    // ==============================
//////    // CREATE TOKEN ENTITY (internal)
//////    // ==============================
//////    @Mapping(target = "id", ignore = true)
//////    @Mapping(target = "user", ignore = true)        // set in service
//////    @Mapping(target = "tokenHash", ignore = true)   // hash in service
//////    @Mapping(target = "expiryDate", ignore = true)  // set in service
//////    @Mapping(target = "revoked", constant = "false")
//////    @Mapping(target = "createdAt", ignore = true)
//////    @Mapping(target = "updatedAt", ignore = true)
//////    RefreshToken toEntity();
//////}
////
//////@Mapper(config = CentralMapperConfig.class)
//////public interface RefreshTokenMapper {
//////
//////    @Mapping(target = "accessToken", ignore = true)
//////    @Mapping(target = "tokenType", constant = "Bearer")
//////    @Mapping(source = "expiryDate", target = "expiresAt")
//////    RefreshTokenResponse toResponseDTO(RefreshToken entity);
//////}
////
////
////import com.apnapg.config.CentralMapperConfig;
////import com.apnapg.entity.RefreshToken;
////import org.mapstruct.*;
////
////@Mapper(config = CentralMapperConfig.class)
////public interface RefreshTokenMapper {
////
////    // ENTITY → DTO (if you ever expose refresh token metadata safely)
////    // Typically, you don’t expose raw tokens, only metadata like expiry.
////    // Example mapping method if needed:
////    // @Mapping(source = "expiresAt", target = "expiresAt")
////    // RefreshTokenResponseDTO toResponseDTO(RefreshToken entity);
////
////    // CREATE DTO → ENTITY
////    @Mapping(target = "id", ignore = true)
////    @Mapping(target = "user", ignore = true)          // set in service
////    @Mapping(target = "tokenHash", ignore = true)     // set in service (hashed)
////    @Mapping(target = "revoked", constant = "false")
////    @Mapping(target = "createdAt", ignore = true)     // auditing
////    @Mapping(target = "updatedAt", ignore = true)     // auditing
////    RefreshToken toEntity(Object dto); // placeholder if you later add a DTO for creation
////}
//package com.apnapg.mappers;
//
//import com.apnapg.config.CentralMapperConfig;
//import com.apnapg.entity.RefreshToken;
//import org.mapstruct.*;
//
//@Mapper(config = CentralMapperConfig.class)
//public interface RefreshTokenMapper {
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "expiryDate", ignore = true)
//    @Mapping(target = "revoked", ignore = true)
//    RefreshToken toEntity(String token);
//}
package com.apnapg.mappers;

import com.apnapg.config.CentralMapperConfig;
import com.apnapg.entity.RefreshToken;
import org.mapstruct.*;

@Mapper(config = CentralMapperConfig.class)
public interface RefreshTokenMapper {

}
