////////package com.apnapg.mappers;
////////
////////import com.apnapg.config.CentralMapperConfig;
////////import com.apnapg.dto.tenant.*;
////////import com.apnapg.entity.Tenant;
////////import com.apnapg.entity.User;
////////import org.mapstruct.*;
////////
//////////@Mapper(config = CentralMapperConfig.class)
//////////public interface TenantMapper {
//////////
//////////    // ==============================
//////////    // Registration → User
//////////    // ==============================
//////////    @Mapping(target = "id", ignore = true)
//////////    @Mapping(target = "role", ignore = true)
//////////    @Mapping(target = "password", ignore = true)
//////////    @Mapping(target = "enabled", constant = "true")
//////////    @Mapping(target = "accountNonLocked", constant = "true")
//////////    User toUserEntity(TenantRegistrationDTO dto);
//////////
//////////    // ==============================
//////////    // Registration → Tenant
//////////    // ==============================
//////////    @Mapping(target = "id", ignore = true)
//////////    @Mapping(target = "user", ignore = true)
//////////    @Mapping(target = "room", ignore = true)
//////////    Tenant toTenantEntity(TenantRegistrationDTO dto);
//////////
//////////    // ==============================
//////////    // Entity → Response DTO
//////////    // ==============================
//////////    @Mapping(target = "email", source = "user.email")
//////////    @Mapping(target = "roomId", expression =
//////////            "java(tenant.getRoom() != null ? tenant.getRoom().getId() : null)")
//////////    TenantResponseDTO toResponseDTO(Tenant tenant);
//////////
//////////    // ==============================
//////////    // Update
//////////    // ==============================
//////////    void updateTenantFromDTO(TenantUpdateDTO dto, @MappingTarget Tenant tenant);
//////////}
////////@Mapper(config = CentralMapperConfig.class)
////////public interface TenantMapper {
////////
////////    @Mapping(target = "id", ignore = true)
////////    @Mapping(target = "room", ignore = true)
////////    @Mapping(target = "user", ignore = true)
////////    @Mapping(target = "aadhaarUrl", ignore = true)
////////    @Mapping(target = "createdAt", ignore = true)
////////    @Mapping(target = "updatedAt", ignore = true)
////////    Tenant toTenantEntity(TenantRegistrationDTO dto);
////////
////////    TenantResponseDTO toResponseDTO(Tenant tenant);
////////
////////    @Mapping(target = "id", ignore = true)
////////    @Mapping(target = "room", ignore = true)
////////    @Mapping(target = "user", ignore = true)
////////    @Mapping(target = "aadhaarUrl", ignore = true)
////////    @Mapping(target = "createdAt", ignore = true)
////////    @Mapping(target = "updatedAt", ignore = true)
////////    void updateTenantFromDTO(TenantUpdateDTO dto, @MappingTarget Tenant tenant);
////////}
//////package com.apnapg.mappers;
//////
//////import com.apnapg.config.CentralMapperConfig;
//////import com.apnapg.dto.tenant.*;
//////import com.apnapg.entity.Tenant;
//////import com.apnapg.entity.User;
//////import org.mapstruct.*;
//////
//////@Mapper(config = CentralMapperConfig.class)
//////public interface TenantMapper {
//////
//////    @Mapping(target = "id", ignore = true)
//////    @Mapping(target = "room", ignore = true)
//////    @Mapping(target = "user", ignore = true)
//////    @Mapping(target = "aadhaarUrl", ignore = true)
//////    @Mapping(target = "createdAt", ignore = true)
//////    @Mapping(target = "updatedAt", ignore = true)
//////    @Mapping(target = "version", ignore = true)
//////    Tenant toTenantEntity(TenantRegistrationDTO dto);
//////
//////    @Mapping(target = "id", ignore = true)
//////    @Mapping(target = "role", ignore = true)
//////    @Mapping(target = "enabled", ignore = true)
//////    @Mapping(target = "accountNonLocked", ignore = true)
//////    @Mapping(target = "failedLoginAttempts", ignore = true)
//////    @Mapping(target = "lockTime", ignore = true)
//////    @Mapping(target = "authProvider", ignore = true)
//////    @Mapping(target = "createdAt", ignore = true)
//////    @Mapping(target = "updatedAt", ignore = true)
//////    User toUserEntity(TenantRegistrationDTO dto);
//////
//////    TenantResponseDTO toResponseDTO(Tenant tenant);
//////
//////    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//////    void updateTenantFromDTO(TenantUpdateDTO dto, @MappingTarget Tenant tenant);
//////}
////package com.apnapg.mappers;
////
////import com.apnapg.config.CentralMapperConfig;
////import com.apnapg.dto.tenant.*;
////import com.apnapg.entity.Tenant;
////import com.apnapg.entity.User;
////import org.mapstruct.*;
////
////@Mapper(config = CentralMapperConfig.class)
////public interface TenantMapper {
////
////    @Mapping(target = "id", ignore = true)
////    @Mapping(target = "room", ignore = true)
////    @Mapping(target = "user", ignore = true)
////    @Mapping(target = "aadhaarUrl", ignore = true)
////    Tenant toTenantEntity(TenantRegistrationDTO dto);
////
////    @Mapping(target = "id", ignore = true)
////    @Mapping(target = "role", ignore = true)
////    @Mapping(target = "enabled", ignore = true)
////    @Mapping(target = "accountNonLocked", ignore = true)
////    @Mapping(target = "failedLoginAttempts", ignore = true)
////    @Mapping(target = "lockTime", ignore = true)
////    @Mapping(target = "authProvider", ignore = true)
////    User toUserEntity(TenantRegistrationDTO dto);
////
////    TenantResponseDTO toResponseDTO(Tenant tenant);
////
////    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
////    void updateTenantFromDTO(TenantUpdateDTO dto, @MappingTarget Tenant tenant);
////}
//package com.apnapg.mappers;
//
//import com.apnapg.config.CentralMapperConfig;
//import com.apnapg.dto.tenant.*;
//import com.apnapg.entity.Tenant;
//import com.apnapg.entity.User;
//import org.mapstruct.*;
//
//@Mapper(config = CentralMapperConfig.class)
//public interface TenantMapper {
//
//    // =====================================================
//    // REGISTRATION → USER ENTITY
//    // =====================================================
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "password", ignore = true) // set in service
//    @Mapping(target = "role", ignore = true)
//    @Mapping(target = "enabled", constant = "true")
//    @Mapping(target = "accountNonLocked", constant = "true")
//    @Mapping(target = "failedLoginAttempts", constant = "0")
//    @Mapping(target = "tenant", ignore = true)
//    @Mapping(target = "owner", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    User toUserEntity(TenantRegistrationDTO dto);
//
//    // =====================================================
//    // REGISTRATION → TENANT ENTITY
//    // =====================================================
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "aadhaarUrl", ignore = true) // set in service
//    @Mapping(target = "user", ignore = true)       // set in service
//    @Mapping(target = "room", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    Tenant toTenantEntity(TenantRegistrationDTO dto);
//
//    // =====================================================
//    // UPDATE
//    // =====================================================
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "aadhaarUrl", ignore = true)
//    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "room", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    void updateTenantFromDTO(TenantUpdateDTO dto,
//                             @MappingTarget Tenant tenant);
//
//    // =====================================================
//    // ENTITY → RESPONSE DTO
//    // =====================================================
//    @Mapping(target = "email",
//            expression = "java(tenant.getUser().getEmail())")
//    @Mapping(target = "roomId",
//            expression = "java(tenant.getRoom() != null ? tenant.getRoom().getId() : null)")
//    TenantResponseDTO toResponseDTO(Tenant tenant);
//}
package com.apnapg.mappers;

import com.apnapg.config.CentralMapperConfig;
import com.apnapg.dto.tenant.*;
import com.apnapg.entity.Tenant;
import com.apnapg.entity.User;
import com.apnapg.enums.AuthProvider;
import org.mapstruct.*;

@Mapper(config = CentralMapperConfig.class)
public interface TenantMapper {

    // =====================================================
    // REGISTRATION → USER ENTITY
    // =====================================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) // set in service
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "accountNonLocked", constant = "true")
    @Mapping(target = "failedLoginAttempts", constant = "0")

    @Mapping(target = "lockTime", ignore = true)          // ✅ FIX
    @Mapping(target = "authProvider", constant = "LOCAL") // ✅ FIX

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "owner", ignore = true)

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUserEntity(TenantRegistrationDTO dto);

    // =====================================================
    // REGISTRATION → TENANT ENTITY
    // =====================================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aadhaarUrl", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Tenant toTenantEntity(TenantRegistrationDTO dto);

    // =====================================================
    // UPDATE TENANT
    // =====================================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aadhaarUrl", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateTenantFromDTO(TenantUpdateDTO dto,
                             @MappingTarget Tenant tenant);

    // =====================================================
    // ENTITY → RESPONSE DTO
    // =====================================================
    @Mapping(target = "email",
            expression = "java(tenant.getUser().getEmail())")
    @Mapping(target = "roomId",
            expression = "java(tenant.getRoom() != null ? tenant.getRoom().getId() : null)")
    TenantResponseDTO toResponseDTO(Tenant tenant);
}
