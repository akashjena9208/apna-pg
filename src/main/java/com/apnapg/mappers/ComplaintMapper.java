//////package com.apnapg.mappers;
//////
//////import com.apnapg.config.CentralMapperConfig;
//////import com.apnapg.dto.complaint.ComplaintCreateDTO;
//////import com.apnapg.dto.complaint.ComplaintResponseDTO;
//////import com.apnapg.entity.Complaint;
//////import org.mapstruct.Mapper;
//////import org.mapstruct.Mapping;
//////
//////@Mapper(config = CentralMapperConfig.class)
//////public interface ComplaintMapper {
//////
//////    Complaint toEntity(ComplaintCreateDTO dto);
//////
//////    @Mapping(source = "tenant.id", target = "tenantId")
//////    @Mapping(source = "pg.id", target = "pgId")
//////    ComplaintResponseDTO toResponseDTO(Complaint complaint);
//////}
////package com.apnapg.mappers;
////
////import com.apnapg.config.CentralMapperConfig;
////import com.apnapg.dto.complaint.*;
////import com.apnapg.entity.Complaint;
////import org.mapstruct.*;
////
////@Mapper(config = CentralMapperConfig.class)
////public interface ComplaintMapper {
////
////    @Mapping(target = "id", ignore = true)
////    @Mapping(target = "tenant", ignore = true)
////    @Mapping(target = "pg", ignore = true)
////    @Mapping(target = "status", ignore = true)
////    @Mapping(target = "createdAt", ignore = true)
////    @Mapping(target = "updatedAt", ignore = true)
////    @Mapping(target = "version", ignore = true)
////    Complaint toEntity(ComplaintCreateDTO dto);
////
////    ComplaintResponseDTO toResponseDTO(Complaint complaint);
////}
//package com.apnapg.mappers;
//
//import com.apnapg.config.CentralMapperConfig;
//import com.apnapg.dto.complaint.*;
//import com.apnapg.entity.Complaint;
//import org.mapstruct.*;
//
//@Mapper(config = CentralMapperConfig.class)
//public interface ComplaintMapper {
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "tenant", ignore = true)
//    @Mapping(target = "pg", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    Complaint toEntity(ComplaintCreateDTO dto);
//
//    ComplaintResponseDTO toResponseDTO(Complaint complaint);
//}
package com.apnapg.mappers;

import com.apnapg.config.CentralMapperConfig;
import com.apnapg.dto.complaint.ComplaintCreateDTO;
import com.apnapg.dto.complaint.ComplaintResponseDTO;
import com.apnapg.entity.Complaint;
import org.mapstruct.*;

@Mapper(config = CentralMapperConfig.class)
public interface ComplaintMapper {

    // =========================================
    // CREATE DTO → ENTITY
    // =========================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pg", ignore = true)        // set manually in service
    @Mapping(target = "tenant", ignore = true)    // set manually in service
    @Mapping(target = "status", ignore = true)    // set manually in service
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Complaint toEntity(ComplaintCreateDTO dto);

    // =========================================
    // ENTITY → RESPONSE DTO
    // =========================================
    @Mapping(target = "pgId", source = "pg.id")
    @Mapping(target = "tenantId", source = "tenant.id")
    ComplaintResponseDTO toResponseDTO(Complaint complaint);
}
