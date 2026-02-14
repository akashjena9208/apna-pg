////package com.apnapg.mappers;
////
////import com.apnapg.config.CentralMapperConfig;
////import com.apnapg.dto.review.ReviewDTO;
////import com.apnapg.dto.review.ReviewResponseDTO;
////import com.apnapg.entity.Review;
////import org.mapstruct.*;
////
////@Mapper(config = CentralMapperConfig.class)
////public interface ReviewMapper {
////
////    @Mapping(target = "id", ignore = true)
////    @Mapping(target = "pg", ignore = true)
////    @Mapping(target = "tenant", ignore = true)
////    Review toEntity(ReviewDTO dto);
////
////    @Mapping(target = "tenantName",
////            expression = "java(review.getTenant().getUser().getEmail())")
////    @Mapping(target = "pgId", source = "pg.id")
////    ReviewResponseDTO toResponseDTO(Review review);
////}
//package com.apnapg.mappers;
//
//import com.apnapg.config.CentralMapperConfig;
//import com.apnapg.dto.review.*;
//import com.apnapg.entity.Review;
//import org.mapstruct.*;
//
//@Mapper(config = CentralMapperConfig.class)
//public interface ReviewMapper {
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "pg", ignore = true)
//    @Mapping(target = "tenant", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "version", ignore = true)
//    Review toEntity(ReviewDTO dto);
//
//    ReviewResponseDTO toResponseDTO(Review review);
//}
package com.apnapg.mappers;

import com.apnapg.config.CentralMapperConfig;
import com.apnapg.dto.review.ReviewDTO;
import com.apnapg.dto.review.ReviewResponseDTO;
import com.apnapg.entity.Review;
import org.mapstruct.*;

@Mapper(config = CentralMapperConfig.class)
public interface ReviewMapper {

    // =========================================
    // CREATE DTO → ENTITY
    // =========================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pg", ignore = true)        // set in service
    @Mapping(target = "tenant", ignore = true)    // set in service
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Review toEntity(ReviewDTO dto);

    // =========================================
    // ENTITY → RESPONSE DTO
    // =========================================
    @Mapping(target = "pgId", source = "pg.id")
    @Mapping(target = "tenantId", source = "tenant.id")
    ReviewResponseDTO toResponseDTO(Review review);
}
