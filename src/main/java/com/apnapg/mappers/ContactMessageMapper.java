//package com.apnapg.mappers;
//
//import com.apnapg.config.CentralMapperConfig;
//import com.apnapg.dto.contact.*;
//import com.apnapg.entity.ContactMessage;
//import org.mapstruct.*;
//
////@Mapper(config = CentralMapperConfig.class)
////public interface ContactMessageMapper {
////
////    // ==============================
////    // ENTITY → RESPONSE DTO
////    // ==============================
////    ContactMessageResponseDTO toResponseDTO(ContactMessage entity);
////
////
////    // ==============================
////    // CREATE DTO → ENTITY
////    // ==============================
////    @Mapping(target = "id", ignore = true)
////    @Mapping(target = "resolved", constant = "false")
////    @Mapping(target = "createdAt", ignore = true)
////    @Mapping(target = "updatedAt", ignore = true)
////    ContactMessage toEntity(ContactMessageDTO dto);
////
////
////    // ==============================
////    // MARK AS RESOLVED
////    // ==============================
////    @BeanMapping(ignoreByDefault = true)
////    @Mapping(target = "resolved", constant = "true")
////    void markResolved(@MappingTarget ContactMessage message);
////}
//@Mapper(config = CentralMapperConfig.class)
//public interface ContactMessageMapper {
//
//    ContactMessageResponseDTO toResponseDTO(ContactMessage entity);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "resolved", constant = "false")
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    ContactMessage toEntity(ContactMessageDTO dto);
//
//    @BeanMapping(ignoreByDefault = true)
//    @Mapping(target = "resolved", constant = "true")
//    void markResolved(@MappingTarget ContactMessage message);
//}
package com.apnapg.mappers;

import com.apnapg.config.CentralMapperConfig;
import com.apnapg.dto.contact.ContactMessageDTO;
import com.apnapg.dto.contact.ContactMessageResponseDTO;
import com.apnapg.entity.ContactMessage;
import org.mapstruct.*;

@Mapper(config = CentralMapperConfig.class)
public interface ContactMessageMapper {

    // ==========================
    // DTO → ENTITY
    // ==========================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "resolved", constant = "false")
    ContactMessage toEntity(ContactMessageDTO dto);

    // ==========================
    // ENTITY → RESPONSE
    // ==========================
    ContactMessageResponseDTO toResponseDTO(ContactMessage entity);
}
