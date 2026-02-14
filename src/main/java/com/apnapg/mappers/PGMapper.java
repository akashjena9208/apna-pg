////////////package com.apnapg.mappers;
////////////
////////////import com.apnapg.config.CentralMapperConfig;
////////////import com.apnapg.dto.pg.PGCreateRequest;
////////////import com.apnapg.dto.pg.PGResponse;
////////////import com.apnapg.entity.PG;
////////////import org.mapstruct.*;
////////////
//////////////@Mapper(config = CentralMapperConfig.class)
//////////////public interface PGMapper {
//////////////
//////////////    // ===============================
//////////////    // CREATE
//////////////    // ===============================
//////////////    @Mapping(target = "id", ignore = true)
//////////////    @Mapping(target = "owner", ignore = true)
//////////////    @Mapping(target = "rooms", ignore = true)
//////////////    @Mapping(target = "imageUrls", ignore = true)
//////////////    PG toEntity(PGCreateRequest dto);
//////////////
//////////////    // ===============================
//////////////    // RESPONSE
//////////////    // ===============================
//////////////    @Mapping(target = "ownerId", source = "owner.id")
//////////////    @Mapping(target = "availableRooms", expression = "java(calculateAvailableRooms(pg))")
//////////////    @Mapping(target = "version", source = "version")
//////////////    PGResponse toResponse(PG pg);
//////////////
//////////////    // ===============================
//////////////    // UPDATE (Partial update safe)
//////////////    // ===============================
//////////////    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//////////////    void updatePGFromDTO(PGCreateRequest dto, @MappingTarget PG entity);
//////////////
//////////////    // ===============================
//////////////    // HELPER METHOD
//////////////    // ===============================
//////////////    default Integer calculateAvailableRooms(PG pg) {
//////////////        if (pg.getRooms() == null) return 0;
//////////////        return (int) pg.getRooms()
//////////////                .stream()
//////////////                .filter(r -> r.getAvailableBeds() > 0)
//////////////                .count();
//////////////    }
//////////////}
////////////@Mapper(config = CentralMapperConfig.class)
////////////public interface PGMapper {
////////////
////////////    @Mapping(target = "id", ignore = true)
////////////    @Mapping(target = "owner", ignore = true)
////////////    @Mapping(target = "rooms", ignore = true)
////////////    @Mapping(target = "imageUrls", ignore = true)
////////////    @Mapping(target = "createdAt", ignore = true)
////////////    @Mapping(target = "updatedAt", ignore = true)
////////////    PG toEntity(PGCreateRequest dto);
////////////
////////////    PGResponse toResponse(PG pg);
////////////
////////////    @Mapping(target = "id", ignore = true)
////////////    @Mapping(target = "owner", ignore = true)
////////////    @Mapping(target = "rooms", ignore = true)
////////////    @Mapping(target = "imageUrls", ignore = true)
////////////    @Mapping(target = "createdAt", ignore = true)
////////////    @Mapping(target = "updatedAt", ignore = true)
////////////    void updatePGFromDTO(PGCreateRequest dto, @MappingTarget PG pg);
////////////}
//////////package com.apnapg.mappers;
//////////
//////////import com.apnapg.config.CentralMapperConfig;
//////////import com.apnapg.dto.pg.*;
//////////import com.apnapg.entity.PG;
//////////import org.mapstruct.*;
//////////
//////////@Mapper(config = CentralMapperConfig.class)
//////////public interface PGMapper {
//////////
//////////    @Mapping(target = "id", ignore = true)
//////////    @Mapping(target = "owner", ignore = true)
//////////    @Mapping(target = "rooms", ignore = true)
//////////    @Mapping(target = "imageUrls", ignore = true)
//////////    @Mapping(target = "createdAt", ignore = true)
//////////    @Mapping(target = "updatedAt", ignore = true)
//////////    @Mapping(target = "version", ignore = true)
//////////    PG toEntity(PGCreateRequest dto);
//////////
//////////    @Mapping(target = "ownerId", source = "owner.id")
//////////    @Mapping(target = "availableRooms", expression = "java(pg.getRooms() != null ? pg.getRooms().size() : 0)")
//////////    PGResponse toResponse(PG pg);
//////////
//////////    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//////////    void updatePGFromDTO(PGCreateRequest dto, @MappingTarget PG pg);
//////////}
////////package com.apnapg.mappers;
////////
////////import com.apnapg.config.CentralMapperConfig;
////////import com.apnapg.dto.pg.*;
////////import com.apnapg.entity.PG;
////////import org.mapstruct.*;
////////
////////@Mapper(config = CentralMapperConfig.class)
////////public interface PGMapper {
////////
////////    @Mapping(target = "id", ignore = true)
////////    @Mapping(target = "owner", ignore = true)
////////    @Mapping(target = "rooms", ignore = true)
////////    @Mapping(target = "imageUrls", ignore = true)
////////    PG toEntity(PGCreateRequest dto);
////////
////////    @Mapping(target = "ownerId", source = "owner.id")
////////    @Mapping(target = "availableRooms",
////////            expression = "java(pg.getRooms() != null ? pg.getRooms().size() : 0)")
////////    PGResponse toResponse(PG pg);
////////
////////    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
////////    void updatePGFromDTO(PGCreateRequest dto, @MappingTarget PG pg);
////////}
//////package com.apnapg.mappers;
//////
//////import com.apnapg.config.CentralMapperConfig;
//////import com.apnapg.dto.pg.*;
//////import com.apnapg.entity.PG;
//////import org.mapstruct.*;
//////
//////@Mapper(config = CentralMapperConfig.class)
//////public interface PGMapper {
//////
//////    // =====================================================
//////    // CREATE PG → ENTITY
//////    // =====================================================
//////    @Mapping(target = "id", ignore = true)
//////    @Mapping(target = "owner", ignore = true)
//////    @Mapping(target = "rooms", ignore = true)
//////    @Mapping(target = "imageUrls", ignore = true)
//////    @Mapping(target = "version", ignore = true)
//////    @Mapping(target = "createdAt", ignore = true)
//////    @Mapping(target = "updatedAt", ignore = true)
//////    PG toEntity(PGCreateRequest dto);
//////
//////
//////    // =====================================================
//////    // UPDATE PG
//////    // =====================================================
//////    @Mapping(target = "id", ignore = true)
//////    @Mapping(target = "owner", ignore = true)
//////    @Mapping(target = "rooms", ignore = true)
//////    @Mapping(target = "imageUrls", ignore = true)
//////    @Mapping(target = "version", ignore = true)
//////    @Mapping(target = "createdAt", ignore = true)
//////    @Mapping(target = "updatedAt", ignore = true)
//////    void updatePGFromDTO(PGCreateRequest dto,
//////                         @MappingTarget PG pg);
//////
//////
//////    // =====================================================
//////    // ENTITY → RESPONSE DTO
//////    // =====================================================
//////    @Mapping(target = "ownerId",
//////            expression = "java(pg.getOwner() != null ? pg.getOwner().getId() : null)")
//////    PGResponse toResponse(PG pg);
//////}
////package com.apnapg.mappers;
////
////import com.apnapg.config.CentralMapperConfig;
////import com.apnapg.dto.pg.*;
////import com.apnapg.entity.PG;
////import org.mapstruct.*;
////
////@Mapper(config = CentralMapperConfig.class)
////public interface PGMapper {
////
////    // =========================================
////    // CREATE
////    // =========================================
////    @Mapping(target = "id", ignore = true)
////    @Mapping(target = "owner", ignore = true)
////    @Mapping(target = "rooms", ignore = true)
////    @Mapping(target = "imageUrls", ignore = true)
////    @Mapping(target = "createdAt", ignore = true)
////    @Mapping(target = "updatedAt", ignore = true)
////    PG toEntity(PGCreateRequest dto);
////
////
////    // =========================================
////    // UPDATE
////    // =========================================
////    @Mapping(target = "id", ignore = true)
////    @Mapping(target = "owner", ignore = true)
////    @Mapping(target = "rooms", ignore = true)
////    @Mapping(target = "imageUrls", ignore = true)
////    @Mapping(target = "createdAt", ignore = true)
////    @Mapping(target = "updatedAt", ignore = true)
////    void updatePGFromDTO(PGCreateRequest dto,
////                         @MappingTarget PG pg);
////
////
////    // =========================================
////    // ENTITY → RESPONSE
////    // =========================================
////    @Mapping(
////            target = "ownerId",
////            expression = "java(pg.getOwner() != null ? pg.getOwner().getId() : null)"
////    )
////    @Mapping(
////            target = "availableRooms",
////            expression = "java(pg.getRooms() == null ? 0 : pg.getRooms().size())"
////    )
////    PGResponse toResponse(PG pg);
////}
//package com.apnapg.mappers;
//
//import com.apnapg.config.CentralMapperConfig;
//import com.apnapg.dto.pg.*;
//import com.apnapg.entity.PG;
//import org.mapstruct.*;
//
//@Mapper(config = CentralMapperConfig.class)
//public interface PGMapper {
//
//    // =========================================
//    // CREATE
//    // =========================================
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "owner", ignore = true)
//    @Mapping(target = "rooms", ignore = true)
//    @Mapping(target = "imageUrls", ignore = true)
//    @Mapping(target = "version", ignore = true)   // ✅ IMPORTANT
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    PG toEntity(PGCreateRequest dto);
//
//
//    // =========================================
//    // UPDATE
//    // =========================================
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "owner", ignore = true)
//    @Mapping(target = "rooms", ignore = true)
//    @Mapping(target = "imageUrls", ignore = true)
//    @Mapping(target = "version", ignore = true)   // ✅ IMPORTANT
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    void updatePGFromDTO(PGCreateRequest dto,
//                         @MappingTarget PG pg);
//
//
//    // =========================================
//    // ENTITY → RESPONSE
//    // =========================================
//    @Mapping(
//            target = "ownerId",
//            expression = "java(pg.getOwner() != null ? pg.getOwner().getId() : null)"
//    )
//    @Mapping(
//            target = "availableRooms",
//            expression = "java(pg.getRooms() == null ? 0 : pg.getRooms().size())"
//    )
//    PGResponse toResponse(PG pg);
//}
package com.apnapg.mappers;

import com.apnapg.config.CentralMapperConfig;
import com.apnapg.dto.pg.*;
import com.apnapg.entity.PG;
import org.mapstruct.*;

@Mapper(config = CentralMapperConfig.class)
public interface PGMapper {

    // ================================
    // CREATE
    // ================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "imageUrls", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PG toEntity(PGCreateRequest dto);

    // ================================
    // UPDATE
    // ================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "imageUrls", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updatePGFromDTO(PGCreateRequest dto,
                         @MappingTarget PG pg);

    // ================================
    // ENTITY → RESPONSE
    // ================================
    @Mapping(
            target = "ownerId",
            expression = "java(pg.getOwner() != null ? pg.getOwner().getId() : null)"
    )
    @Mapping(
            target = "availableRooms",
            expression = "java(pg.getRooms() == null ? 0 : pg.getRooms().size())"
    )
    PGResponse toResponse(PG pg);
}
