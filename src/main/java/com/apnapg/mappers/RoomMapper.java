//////////package com.apnapg.mappers;
//////////
//////////import com.apnapg.config.CentralMapperConfig;
//////////import com.apnapg.dto.room.RoomCreateDTO;
//////////import com.apnapg.dto.room.RoomResponseDTO;
//////////import com.apnapg.entity.Room;
//////////import org.mapstruct.*;
//////////
//////////@Mapper(config = CentralMapperConfig.class)
//////////public interface RoomMapper {
//////////
//////////    // CREATE
//////////    @Mapping(target = "id", ignore = true)
//////////    @Mapping(target = "pg", ignore = true)
//////////    @Mapping(target = "tenants", ignore = true)
//////////    @Mapping(target = "availableBeds", source = "totalBeds")
//////////    Room toEntity(RoomCreateDTO dto);
//////////
//////////    // RESPONSE
//////////    @Mapping(target = "occupiedBeds",
//////////            expression = "java(room.getTotalBeds() - room.getAvailableBeds())")
//////////    RoomResponseDTO toResponseDTO(Room room);
//////////}
////////package com.apnapg.mappers;
////////
////////import com.apnapg.config.CentralMapperConfig;
////////import com.apnapg.dto.room.*;
////////import com.apnapg.entity.Room;
////////import org.mapstruct.*;
////////
////////@Mapper(config = CentralMapperConfig.class)
////////public interface RoomMapper {
////////
////////    @Mapping(target = "id", ignore = true)
////////    @Mapping(target = "pg", ignore = true)
////////    @Mapping(target = "tenants", ignore = true)
////////    @Mapping(target = "createdAt", ignore = true)
////////    @Mapping(target = "updatedAt", ignore = true)
////////    @Mapping(target = "version", ignore = true)
////////    Room toEntity(RoomCreateDTO dto);
////////
////////    RoomResponseDTO toResponseDTO(Room room);
////////}
//////package com.apnapg.mappers;
//////
//////import com.apnapg.config.CentralMapperConfig;
//////import com.apnapg.dto.room.*;
//////import com.apnapg.entity.Room;
//////import org.mapstruct.*;
//////
//////@Mapper(config = CentralMapperConfig.class)
//////public interface RoomMapper {
//////
//////    @Mapping(target = "id", ignore = true)
//////    @Mapping(target = "pg", ignore = true)
//////    @Mapping(target = "tenants", ignore = true)
//////    @Mapping(target = "createdAt", ignore = true)
//////    @Mapping(target = "updatedAt", ignore = true)
//////    @Mapping(target = "version", ignore = true)
//////    Room toEntity(RoomCreateDTO dto);
//////
//////    RoomResponseDTO toResponseDTO(Room room);
//////}
////package com.apnapg.mappers;
////
////import com.apnapg.config.CentralMapperConfig;
////import com.apnapg.dto.room.*;
////import com.apnapg.entity.Room;
////import org.mapstruct.*;
////
////@Mapper(config = CentralMapperConfig.class)
////public interface RoomMapper {
////
////    @Mapping(target = "id", ignore = true)
////    @Mapping(target = "pg", ignore = true)
////    @Mapping(target = "tenants", ignore = true)
////    Room toEntity(RoomCreateDTO dto);
////
////    RoomResponseDTO toResponseDTO(Room room);
////}
//package com.apnapg.mappers;
//
//import com.apnapg.config.CentralMapperConfig;
//import com.apnapg.dto.room.RoomCreateDTO;
//import com.apnapg.dto.room.RoomResponseDTO;
//import com.apnapg.dto.room.RoomUpdateDTO;
//import com.apnapg.entity.Room;
//import org.mapstruct.*;
//
//@Mapper(config = CentralMapperConfig.class)
//public interface RoomMapper {
//
//    // =========================================
//    // CREATE DTO → ENTITY
//    // =========================================
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "pg", ignore = true) // set in service
//    @Mapping(target = "availableBeds", source = "totalBeds") // initially all beds free
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "version", ignore = true)
//    Room toEntity(RoomCreateDTO dto);
//
//    // =========================================
//    // UPDATE DTO → ENTITY
//    // =========================================
//    @Mapping(target = "pg", ignore = true)
//    @Mapping(target = "availableBeds", ignore = true) // handled manually
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "version", ignore = true)
//    void updateRoomFromDTO(RoomUpdateDTO dto, @MappingTarget Room room);
//
//    // =========================================
//    // ENTITY → RESPONSE DTO
//    // =========================================
//    @Mapping(target = "occupiedBeds", expression =
//            "java(room.getTotalBeds() - room.getAvailableBeds())")
//    RoomResponseDTO toResponseDTO(Room room);
//}
package com.apnapg.mappers;

import com.apnapg.config.CentralMapperConfig;
import com.apnapg.dto.room.*;
import com.apnapg.entity.Room;
import org.mapstruct.*;

@Mapper(config = CentralMapperConfig.class)
public interface RoomMapper {

    // =====================================================
    // CREATE ROOM → ENTITY
    // =====================================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pg", ignore = true)
    @Mapping(target = "tenants", ignore = true)   // ✅ FIX
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)

    // availableBeds = totalBeds initially
    @Mapping(target = "availableBeds",
            expression = "java(dto.totalBeds())")
    Room toEntity(RoomCreateDTO dto);


    // =====================================================
    // UPDATE ROOM
    // =====================================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pg", ignore = true)
    @Mapping(target = "tenants", ignore = true)   // ✅ FIX
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "availableBeds", ignore = true)
    void updateRoomFromDTO(RoomUpdateDTO dto,
                           @MappingTarget Room room);


    // =====================================================
    // ENTITY → RESPONSE DTO
    // =====================================================
    @Mapping(target = "occupiedBeds",
            expression = "java(room.getTotalBeds() - room.getAvailableBeds())")
    RoomResponseDTO toResponseDTO(Room room);
}
